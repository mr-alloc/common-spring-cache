package io.alloc.cache.annotation.hash

/**
 * Redis HLEN (HASH 길이 조회)
 * 반환타입인 Int 또는 Long으로 가능
 * @see io.alloc.cache.handler.hash.RedisHashLenHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisHashLen(val cacheKey: String)
