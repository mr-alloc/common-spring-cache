package io.alloc.cache.annotation.hash

/**
 * Redis HDEL
 * @see io.alloc.cache.handler.hash.RedisHashEvictHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param hashKey       (SpEL) 삭제 할 Hash 필드
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisHashEvict(
    val cacheKey: String,
    val hashKey: String,
)
