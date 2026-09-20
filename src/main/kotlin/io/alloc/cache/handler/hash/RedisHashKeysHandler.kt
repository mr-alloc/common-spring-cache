package io.alloc.cache.handler.hash

import io.alloc.cache.annotation.hash.RedisHashKeys
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class RedisHashKeysHandler(
    private val redisTemplate: RedisTemplate<String, String>
) : RedisHashHandler<RedisHashKeys>(redisTemplate) {

    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisHashKeys): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)
        val rawKeys = operation.keys(key) ?: return emptySet<Any>()
        return rawKeys.mapNotNull { convert(it, annotation.elementType) }.toSet()
    }

    private fun convert(value: String, target: KClass<*>): Any? {
        return when (target) {
            String::class -> value
            Int::class -> value.toIntOrNull()
            Long::class -> value.toLongOrNull()
            Double::class -> value.toDoubleOrNull()
            Float::class -> value.toFloatOrNull()
            Short::class -> value.toShortOrNull()
            Byte::class -> value.toByteOrNull()
            else -> value
        }
    }
}