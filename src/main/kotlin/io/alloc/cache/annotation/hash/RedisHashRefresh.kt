package io.alloc.cache.annotation.hash

/**
 * Redis HGET, HSET(HASH 새로고침, 원본 메서드의 값을 캐시에 재적용)
 * @see io.alloc.cache.handler.hash.RedisHashRefreshHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param ttl           만료 시간(초), 0이면 TTL 없음
 * @param nullable      false면 원본 메서드가 null 반환 시 예외 발생
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisHashRefresh(
    val cacheKey: String,
    val ttl: Long = 0L,
    val nullable: Boolean = true,
)