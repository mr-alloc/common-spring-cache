package io.alloc.cache.aspect

import io.alloc.cache.annotation.zset.RedisZSetScore
import io.alloc.cache.annotation.zset.RedisZSetGetAll
import io.alloc.cache.annotation.zset.RedisZSetSet
import io.alloc.cache.handler.zset.RedisZSetGetAllHandler
import io.alloc.cache.handler.zset.RedisZSetScoreHandler
import io.alloc.cache.handler.zset.RedisZSetSetHandler
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class RedisZSetAspect(
    private val setHandler: RedisZSetSetHandler,
    private val getAllHandler: RedisZSetGetAllHandler,
    private val scoreHandler: RedisZSetScoreHandler
) {

    @Around("@annotation(set)")
    fun handle(jointPoint: ProceedingJoinPoint, set: RedisZSetSet) =
        setHandler.handle(jointPoint, set)

    @Around("@annotation(getAll)")
    fun handle(joinPoint: ProceedingJoinPoint, getAll: RedisZSetGetAll) =
        getAllHandler.handle(joinPoint, getAll)

    @Around("@annotation(score)")
    fun handle(joinPoint: ProceedingJoinPoint, score: RedisZSetScore) =
        scoreHandler.handle(joinPoint, score)
}