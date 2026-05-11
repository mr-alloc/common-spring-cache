package io.alloc.cache.handler.value

import io.alloc.cache.annotation.value.RedisRefresh
import io.alloc.cache.common.exception.CacheProceedNullException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class RedisRefreshHandler(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : RedisValueHandler<RedisRefresh>(redisTemplate) {
    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisRefresh): Any? {
        val signature = joinPoint.signature as MethodSignature

        val paramMap = getParamMap(signature.method, joinPoint.args)
        val key = resolveKey(annotation.cacheKey, paramMap)

        val result = joinPoint.proceed() ?: run {
            if (!annotation.nullable) throw CacheProceedNullException(signature.method.name)
            return null
        }

        redisTemplate.execute(
            REFRESH_SCRIPT,
            listOf(key),
            objectMapper.writeValueAsString(result),
            annotation.ttl.toString()
        )
        return result
    }

    companion object {
        private val REFRESH_SCRIPT = RedisScript.of<Long>(
            """
            redis.call('SET', KEYS[1], ARGV[1])
            local ttl = tonumber(ARGV[2])
            if ttl > 0 then
                redis.call('EXPIRE', KEYS[1], ttl)
            end
            return 1
            """.trimIndent(),
            Long::class.java
        )
    }
}