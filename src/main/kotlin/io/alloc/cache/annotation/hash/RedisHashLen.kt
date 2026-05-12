package io.alloc.cache.annotation.hash

/**
 * Redis HLEN (HASH 길이 조회)
 * @see io.alloc.cache.handler.hash.RedisHashLenHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisHashLen(val cacheKey: String)
