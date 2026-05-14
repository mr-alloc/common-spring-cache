package io.alloc.cache.handler.value

import io.alloc.cache.annotation.value.RedisGet
import io.alloc.cache.common.exception.CacheProceedNullException
import kotlinx.coroutines.runBlocking
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper
import java.util.concurrent.TimeUnit

@Component
class RedisGetHandler(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : RedisValueHandler<RedisGet>(redisTemplate) {

    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisGet): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)
        operation.get(key)?.let { found ->
            return objectMapper.readValue(found, signature.returnType)
        }

        //캐시 미스 -> 원본 메서드 실행
        val result = runBlocking { joinPoint.proceedWithSuspend() }
            ?: run {
                if (!annotation.nullable) throw CacheProceedNullException(signature.method.name)
                return null
            }
        operation.set(key, objectMapper.writeValueAsString(result))
        if (annotation.ttl > 0) {
            redisTemplate.expire(key, annotation.ttl, TimeUnit.SECONDS)
        }

        return result
    }
}