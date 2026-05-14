package io.alloc.cache.annotation.hash

/**
 * Redis HKEYS (해시 키 목록 조회)
 * 반환 타입은 Set으로 지정
 * @see io.alloc.cache.handler.hash.RedisHashKeysHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisHashKeys(val cacheKey: String)
