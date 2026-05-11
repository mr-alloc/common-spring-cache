package io.alloc.cache.aspect

import io.alloc.cache.annotation.hash.RedisHashEvict
import io.alloc.cache.annotation.hash.RedisHashGet
import io.alloc.cache.annotation.hash.RedisHashGetAll
import io.alloc.cache.annotation.hash.RedisHashRefresh
import io.alloc.cache.annotation.hash.RedisHashSet
import io.alloc.cache.handler.hash.RedisHashEvictHandler
import io.alloc.cache.handler.hash.RedisHashGetAllHandler
import io.alloc.cache.handler.hash.RedisHashGetHandler
import io.alloc.cache.handler.hash.RedisHashRefreshHandler
import io.alloc.cache.handler.hash.RedisHashSetHandler
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class RedisHashAspect(
    // Hash
    private val hashGetHandler: RedisHashGetHandler,
    private val hashSetHandler: RedisHashSetHandler,
    private val hashEvictHandler: RedisHashEvictHandler,
    private val hashGetAllHandler: RedisHashGetAllHandler,
    private val hashRefreshHandler: RedisHashRefreshHandler,
) {

    @Around("@annotation(redisHashGet)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashGet: RedisHashGet) =
        hashGetHandler.handle(joinPoint, redisHashGet)

    @Around("@annotation(redisHashSet)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashSet: RedisHashSet) =
        hashSetHandler.handle(joinPoint, redisHashSet)

    @Around("@annotation(redisHashEvict)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashEvict: RedisHashEvict) =
        hashEvictHandler.handle(joinPoint, redisHashEvict)

    @Around("@annotation(redisHashGetAll)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashGetAll: RedisHashGetAll) =
        hashGetAllHandler.handle(joinPoint, redisHashGetAll)

    @Around("@annotation(redisHashRefresh)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashRefresh: RedisHashRefresh) =
        hashRefreshHandler.handle(joinPoint, redisHashRefresh)

}