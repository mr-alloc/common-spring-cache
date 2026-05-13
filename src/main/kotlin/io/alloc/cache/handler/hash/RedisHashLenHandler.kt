package io.alloc.cache.handler.hash

import io.alloc.cache.annotation.hash.RedisHashLen
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper
import kotlin.reflect.KClass
import kotlin.reflect.jvm.kotlinFunction

@Component
class RedisHashLenHandler(
    private val redisTemplate: RedisTemplate<String, String>
) : RedisHashHandler<RedisHashLen>(redisTemplate) {
    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisHashLen): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val returnType = signature.method.resolveReturnType()
            ?: throw IllegalStateException("Method ${signature.method.name}의 반환 타입을 확인할 수 없습니다.")
        if (returnType !in NUMBER_TYPES)
            throw IllegalStateException("${signature.method.name}의 반환 타입이 정수가 아닙니다: ${signature.method.returnType}")

        val key = resolveKey(annotation.cacheKey, paramMap)
        val size = operation.size(key)
        return when (signature.method.returnType) {
            Int::class.java, Int::class.javaObjectType -> size.toInt()
            Long::class.java, Long::class.javaObjectType -> size.toLong()
            else -> size
        }
    }
    companion object {
        private val NUMBER_TYPES = setOf<KClass<*>>(Int::class, Long::class)
    }
}