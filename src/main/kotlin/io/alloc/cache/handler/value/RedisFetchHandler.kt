package io.alloc.cache.handler.value

import io.alloc.cache.annotation.value.RedisFetch
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class RedisFetchHandler(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : RedisValueHandler<RedisFetch>(redisTemplate) {
    override fun handle(
        joinPoint: ProceedingJoinPoint,
        annotation: RedisFetch,
    ): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)
        operation.get(key)?.let { found ->
            return objectMapper.readValue(found, signature.returnType)
        }
        // 캐시 미스 시 원본 메서드를 실행하지 않고 null 반환 (의도된 동작)
        return null
    }
}