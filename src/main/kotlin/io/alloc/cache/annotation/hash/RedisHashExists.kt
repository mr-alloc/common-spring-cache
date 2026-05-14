package io.alloc.cache.annotation.hash

/**
 * Redis HEXISTS 키 존재 여부 확인
 * 반환 타입은 Boolean으로 지정
 * @see io.alloc.cache.handler.hash.RedisHashExistsHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param hashKey       (SpEL) HASH 키
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisHashExists(
    val cacheKey: String,
    val hashKey: String
)
