package io.alloc.cache.handler.zset

import io.alloc.cache.annotation.zset.RedisZSetGetAll
import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class RedisZSetGetAllHandler(
    private val redisTemplate: RedisTemplate<String, String>,
) : RedisZSetHandler<RedisZSetGetAll>(redisTemplate) {

    override fun handle(joinPoint: ProceedingJoinPoint, annotation: RedisZSetGetAll): Any? {
        return operation.rangeWithScores(annotation.cacheKey, 0, -1)
            ?.associate { tuple ->
                val member = convertType(tuple.value!!, annotation.memberType)
                val score = convertType(tuple.score!!.toInt().toString(), annotation.scoreType)
                member to score
            }
            ?: emptyMap<Any, Any>()
    }

    private fun convertType(value: String, type: KClass<*>): Any = when (type) {
        Int::class    -> value.toInt()
        Long::class   -> value.toLong()
        Double::class -> value.toDouble()
        String::class -> value
        else          -> throw IllegalArgumentException("Unsupported type $type")
    }
}