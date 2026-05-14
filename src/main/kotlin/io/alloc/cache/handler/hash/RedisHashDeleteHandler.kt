package io.alloc.cache.handler.hash

import io.alloc.cache.annotation.hash.RedisHashDelete
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisHashDeleteHandler(
    private val redisTemplate: RedisTemplate<String, String>
) : RedisHashHandler<RedisHashDelete>(redisTemplate) {
    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisHashDelete): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)
        val hashKeys = if (annotation.hashKey.contains("$")) {
            resolveCollectionKey(paramMap, annotation.hashKey)
        } else {
            listOf(resolveParam(paramMap, annotation.hashKey).toString())
        }.toTypedArray()

        return operation.delete(key, hashKeys)
    }
}