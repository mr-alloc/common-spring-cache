package io.alloc.cache.handler.zset

import io.alloc.cache.annotation.zset.RedisZSetScore
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class RedisZSetScoreHandler(
    private val redisTemplate: RedisTemplate<String, String>,
) : RedisZSetHandler<RedisZSetScore>(redisTemplate) {

    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisZSetScore): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)
        val member = resolveParam(paramMap, annotation.member)

        val score = this.operation.score(key, member)
            ?: return joinPoint.proceed()

        return score.to(annotation.scoreType)
    }

    private fun Double.to(type: KClass<*>): Any = when (type) {
        Int::class    -> this.toInt()
        Long::class   -> this.toLong()
        String::class -> this.toString()
        Double::class  -> this
        else          -> throw IllegalArgumentException("Unsupported type $type")
    }
}