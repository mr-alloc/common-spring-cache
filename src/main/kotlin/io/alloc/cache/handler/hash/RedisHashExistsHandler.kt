package io.alloc.cache.handler.hash

import io.alloc.cache.annotation.hash.RedisHashDelete
import io.alloc.cache.annotation.hash.RedisHashExists
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisHashExistsHandler(
    private val redisTemplate: RedisTemplate<String, String>
) : RedisHashHandler<RedisHashExists>(redisTemplate) {
    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisHashExists): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)
        val hashKey = resolveParam(paramMap, annotation.hashKey).toString()

        return operation.hasKey(key, hashKey)
    }
}