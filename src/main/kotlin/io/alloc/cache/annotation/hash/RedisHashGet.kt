package io.alloc.cache.annotation.hash

/**
 * Redis HGET (Hash 자료 구조에서 조회)
 * @see io.alloc.cache.handler.hash.RedisHashGetHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param hashKey       (SpEL) HASH 키
 * @param ttl           만료 시간(초), 0이면 TTL 없음
 * @param nullable      false면 원본 메서드가 null 반환 시 예외 발생
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisHashGet(
    val cacheKey: String,
    val hashKey: String,
    val ttl: Long = 0L,
    val nullable: Boolean = true,
)
