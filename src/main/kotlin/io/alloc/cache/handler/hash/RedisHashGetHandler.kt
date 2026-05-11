package io.alloc.cache.handler.hash

import io.alloc.cache.common.exception.CacheProceedNullException
import io.alloc.cache.annotation.hash.RedisHashGet
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper
import java.util.concurrent.TimeUnit

@Component
class RedisHashGetHandler(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) : RedisHashHandler<RedisHashGet>(
    redisTemplate
) {

    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisHashGet): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)
        val hashKey = resolveParam(paramMap, annotation.hashKey).toString()

        operation.get(key, hashKey)?.let { found ->
            return objectMapper.readValue(found, signature.returnType)
        }

        //캐시 미스 -> 원본 메서드 실행
        val result = joinPoint.proceed()
            ?: run {
                if (!annotation.nullable) throw CacheProceedNullException(signature.method.name)
                return null
            }
        operation.put(key, hashKey.toString(), objectMapper.writeValueAsString(result))
        if (annotation.ttl > 0) {
            redisTemplate.expire(key, annotation.ttl, TimeUnit.SECONDS)
        }

        return result
    }
}