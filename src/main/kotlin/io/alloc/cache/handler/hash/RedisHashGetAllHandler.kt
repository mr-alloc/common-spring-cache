package io.alloc.cache.handler.hash

import io.alloc.cache.annotation.hash.RedisHashGetAll
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper
import java.util.concurrent.TimeUnit

@Component
class RedisHashGetAllHandler(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) : RedisHashHandler<RedisHashGetAll>(redisTemplate) {

    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisHashGetAll): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)
        val key = resolveKey(annotation.cacheKey, paramMap)

        // 캐시 히트: 전체 Hash 반환
        val cached = operation.entries(key)
        if (cached.isNotEmpty()) {
            // 반환 타입에서 value 의 제네릭 타입 추론
            return cached.entries.associate { entry ->
                entry.key to objectMapper.readValue(entry.value, annotation.valueType.java)
            }
        }

        // 캐시 미스 → 원본 실행 (Map<String, *> 기대)
        val result = (joinPoint.proceed() as? Map<*, *>) ?: return null

        val toStore = result.entries.associate { (k, v) ->
            k.toString() to objectMapper.writeValueAsString(v)
        }
        operation.putAll(key, toStore)
        if (annotation.ttl > 0) {
            redisTemplate.expire(key, annotation.ttl, TimeUnit.SECONDS)
        }
        return result
    }
}