package io.alloc.cache.handler.hash

import io.alloc.cache.annotation.hash.RedisHashRefresh
import io.alloc.cache.common.CacheHandler
import io.alloc.cache.common.exception.CacheProceedNullException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper
import kotlin.collections.component1
import kotlin.collections.component2

@Component
class RedisHashRefreshHandler(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) : CacheHandler<RedisHashRefresh>() {

    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisHashRefresh): Any? {
        val signature = joinPoint.signature as MethodSignature

        val paramMap = getParamMap(signature.method, joinPoint.args)
        val key = resolveKey(annotation.cacheKey, paramMap)

        //리프레시는 원본 결과를 먼저 실행
        val result = joinPoint.proceed() ?: run {
            if (!annotation.nullable)
                throw CacheProceedNullException(signature.method.name)
            return null
        }

        val newEntries = when (result) {
            is Map<*, *> -> result.entries
                .associate { (k, v) -> k.toString() to objectMapper.writeValueAsString(v) }
            else -> throw IllegalArgumentException(
                "${signature.method.name} 의 반환타입이 Map이 아닙니다: ${result::class.simpleName}"
            )
        }

        val args = buildList {
            addAll(newEntries.flatMap { (k, v) -> listOf(k, v) })
            add(annotation.ttl.toString())//TTL은 가장 마지막에
        }

        redisTemplate.execute(REFRESH_SCRIPT, listOf(key), *args.toTypedArray())
        return result
    }

    companion object {
        private val REFRESH_SCRIPT = RedisScript.of<Long>(
            """
            redis.call('DEL', KEYS[1])
            redis.call('HSET', KEYS[1], unpack(ARGV, 1, #ARGV - 1))
            local ttl = tonumber(ARGV[#ARGV])
            if ttl > 0 then
                redis.call('EXPIRE', KEYS[1], ttl)
            end
            return 1
            """.trimIndent(),
            Long::class.java
        )
    }
}