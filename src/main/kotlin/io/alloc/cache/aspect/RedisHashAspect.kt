package io.alloc.cache.aspect

import io.alloc.cache.annotation.hash.RedisHashDelete
import io.alloc.cache.annotation.hash.RedisHashEvict
import io.alloc.cache.annotation.hash.RedisHashExists
import io.alloc.cache.annotation.hash.RedisHashFetch
import io.alloc.cache.annotation.hash.RedisHashGet
import io.alloc.cache.annotation.hash.RedisHashGetAll
import io.alloc.cache.annotation.hash.RedisHashKeys
import io.alloc.cache.annotation.hash.RedisHashLen
import io.alloc.cache.annotation.hash.RedisHashMultiGet
import io.alloc.cache.annotation.hash.RedisHashMultiSet
import io.alloc.cache.annotation.hash.RedisHashRefresh
import io.alloc.cache.annotation.hash.RedisHashSet
import io.alloc.cache.annotation.hash.RedisHashValues
import io.alloc.cache.handler.hash.RedisHashDeleteHandler
import io.alloc.cache.handler.hash.RedisHashEvictHandler
import io.alloc.cache.handler.hash.RedisHashExistsHandler
import io.alloc.cache.handler.hash.RedisHashFetchHandler
import io.alloc.cache.handler.hash.RedisHashGetAllHandler
import io.alloc.cache.handler.hash.RedisHashGetHandler
import io.alloc.cache.handler.hash.RedisHashKeysHandler
import io.alloc.cache.handler.hash.RedisHashLenHandler
import io.alloc.cache.handler.hash.RedisHashMultiGetHandler
import io.alloc.cache.handler.hash.RedisHashMultiSetHandler
import io.alloc.cache.handler.hash.RedisHashRefreshHandler
import io.alloc.cache.handler.hash.RedisHashSetHandler
import io.alloc.cache.handler.hash.RedisHashValuesHandler
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
    private val hashLenHandler: RedisHashLenHandler,
    private val hashFetchHandler: RedisHashFetchHandler,
    private val hashDeleteHandler: RedisHashDeleteHandler,
    private val hashExistsHandler: RedisHashExistsHandler,
    private val hashKeysHandler: RedisHashKeysHandler,
    private val hashMultiGetHandler: RedisHashMultiGetHandler,
    private val hashMultiSetHandler: RedisHashMultiSetHandler,
    private val hashValuesHandler: RedisHashValuesHandler
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

    @Around("@annotation(redisHashLen)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashLen: RedisHashLen) =
        hashLenHandler.handle(joinPoint, redisHashLen)

    @Around("@annotation(redisHashFetch)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashFetch: RedisHashFetch) =
        hashFetchHandler.handle(joinPoint, redisHashFetch)

    @Around("@annotation(redisHashDelete)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashDelete: RedisHashDelete) =
        hashDeleteHandler.handle(joinPoint, redisHashDelete)

    @Around("@annotation(redisHashExists)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashExists: RedisHashExists) =
        hashExistsHandler.handle(joinPoint, redisHashExists)

    @Around("@annotation(redisHashKeys)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashKeys: RedisHashKeys) =
        hashKeysHandler.handle(joinPoint, redisHashKeys)

    @Around("@annotation(redisHashMultiGet)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashMultiGet: RedisHashMultiGet) =
        hashMultiGetHandler.handle(joinPoint, redisHashMultiGet)

    @Around("@annotation(redisHashMultiSet)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashMultiSet: RedisHashMultiSet) =
        hashMultiSetHandler.handle(joinPoint, redisHashMultiSet)

    @Around("@annotation(redisHashValues)")
    fun handle(joinPoint: ProceedingJoinPoint, redisHashValues: RedisHashValues) =
        hashValuesHandler.handle(joinPoint, redisHashValues)
}