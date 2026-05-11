package io.alloc.cache.annotation.hash

/**
 * Redis HSET (원본 메서드 실행 후 Hash 필드에 저장)
 * @see io.alloc.cache.handler.hash.RedisHashSetHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param hashKey       (SpEL) 삭제 할 Hash 필드
 * @param ttl           만료 시간(초), 0이면 TTL 없음
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisHashSet(
    val cacheKey: String,
    val hashKey: String,
    val ttl: Long = 0L,
)
