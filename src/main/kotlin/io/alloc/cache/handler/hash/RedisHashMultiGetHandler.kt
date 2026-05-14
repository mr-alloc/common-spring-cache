package io.alloc.cache.handler.hash

import io.alloc.cache.annotation.hash.RedisHashMultiGet
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class RedisHashMultiGetHandler(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : RedisHashHandler<RedisHashMultiGet>(redisTemplate) {
    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisHashMultiGet): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)
        val hashKeys = if (annotation.hashKey.contains("$")) {
            resolveCollectionKey(paramMap, annotation.hashKey)
        } else {
            listOf(resolveParam(paramMap, annotation.hashKey).toString())
        }.toMutableList()

        return operation.multiGet(key, hashKeys)
            .map { objectMapper.readValue(it, annotation.valueType.java) }
    }
}