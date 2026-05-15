package io.alloc.cache.handler.value

import io.alloc.cache.annotation.value.RedisDelete
import io.alloc.cache.common.CacheHandler
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisDeleteHandler(
    private val redisTemplate: RedisTemplate<String, String>
) : CacheHandler<RedisDelete>() {
    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisDelete): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)
        return redisTemplate.delete(key)
    }
}