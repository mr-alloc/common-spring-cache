package io.alloc.cache.handler.hash

import io.alloc.cache.annotation.hash.RedisHashEvict
import kotlinx.coroutines.runBlocking
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisHashEvictHandler(
    redisTemplate: RedisTemplate<String, String>,
) : RedisHashHandler<RedisHashEvict>(redisTemplate) {

    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisHashEvict): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)
        val hashKey = resolveParam(paramMap, annotation.hashKey).toString()

        val result = runBlocking { joinPoint.proceedWithSuspend() } ?: return null

        operation.delete(key, hashKey)

        return result
    }
}