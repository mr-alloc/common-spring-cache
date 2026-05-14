package io.alloc.cache.handler.value

import io.alloc.cache.annotation.value.RedisSet
import kotlinx.coroutines.runBlocking
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper
import java.util.concurrent.TimeUnit

@Component
class RedisSetHandler(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) : RedisValueHandler<RedisSet>(redisTemplate) {
    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisSet): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)

        val result = runBlocking { joinPoint.proceedWithSuspend() } ?: return null
        operation.set(key, objectMapper.writeValueAsString(result))
        if (annotation.ttl > 0) {
            redisTemplate.expire(key, annotation.ttl, TimeUnit.SECONDS)
        }
        return result
    }
}