package io.alloc.cache.aspect

import io.alloc.cache.annotation.hash.RedisHashGet
import io.alloc.cache.annotation.value.RedisGet
import io.alloc.cache.annotation.value.RedisRefresh
import io.alloc.cache.annotation.value.RedisSet
import io.alloc.cache.handler.value.RedisGetHandler
import io.alloc.cache.handler.value.RedisRefreshHandler
import io.alloc.cache.handler.value.RedisSetHandler
import io.alloc.cache.handler.value.RedisValueHandler
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class RedisValueAspect(
    private val redisSetHandler: RedisSetHandler,
    private val redisGetHandler: RedisGetHandler,
    private val redisRefreshHandler: RedisRefreshHandler
) {

    @Around("@annotation(redisSet)")
    fun handle(joinPoint: ProceedingJoinPoint, redisSet: RedisSet) =
        redisSetHandler.handle(joinPoint, redisSet)

    @Around("@annotation(redisGet)")
    fun handle(joinPoint: ProceedingJoinPoint, redisGet: RedisGet) =
        redisGetHandler.handle(joinPoint, redisGet)

    @Around("@annotation(redisRefresh)")
    fun handle(joinPoint: ProceedingJoinPoint, redisRefresh: RedisRefresh) =
        redisRefreshHandler.handle(joinPoint, redisRefresh)
}