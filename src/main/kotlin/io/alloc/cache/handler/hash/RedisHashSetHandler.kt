package io.alloc.cache.handler.hash

import io.alloc.cache.annotation.hash.RedisHashSet
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper
import java.util.concurrent.TimeUnit

@Component
class RedisHashSetHandler(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : RedisHashHandler<RedisHashSet>(redisTemplate) {

    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisHashSet): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)
        val hashKey = resolveParam(paramMap, annotation.hashKey).toString()

        val result = joinPoint.proceed() ?: return null
        operation.put(key, hashKey, objectMapper.writeValueAsString(result))
        if (annotation.ttl > 0) {
            redisTemplate.expire(key, annotation.ttl, TimeUnit.SECONDS)
        }
        return result
    }
}