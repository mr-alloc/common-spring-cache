package io.alloc.cache.handler.zset

import io.alloc.cache.annotation.zset.RedisZSetSet
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisZSetSetHandler(
    private val redisTemplate: RedisTemplate<String, String>
) : RedisZSetHandler<RedisZSetSet>(redisTemplate) {

    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisZSetSet): Any? {
        val signature = joinPoint.signature as MethodSignature
        val paramMap = getParamMap(signature.method, joinPoint.args)

        val key = resolveKey(annotation.cacheKey, paramMap)
        val member = resolveParam(paramMap, annotation.member).toString()
        val score = resolveParam(paramMap, annotation.score).toString()
        return operation.add(key, member, score.toDouble())
    }
}