package io.alloc.cache.handler.hash

import io.alloc.cache.annotation.hash.RedisHashMultiSet
import kotlinx.coroutines.runBlocking
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper
import java.util.concurrent.TimeUnit

@Component
class RedisHashMultiSetHandler(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : RedisHashHandler<RedisHashMultiSet>(redisTemplate) {
    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisHashMultiSet): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)
        val key = resolveKey(annotation.cacheKey, paramMap)

        val map = paramMap[annotation.hashMap] as? Map<*, *> ?: return null

        // 직렬화 후 Redis 저장
        val entries = map.entries.associate { (k, v) ->
            k.toString() to objectMapper.writeValueAsString(v)
        }

        operation.putAll(key, entries)

        if (annotation.ttl > 0) {
            redisTemplate.expire(key, annotation.ttl, TimeUnit.SECONDS)
        }

        return map
    }
}