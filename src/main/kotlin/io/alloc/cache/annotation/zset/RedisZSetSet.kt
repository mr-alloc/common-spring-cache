package io.alloc.cache.annotation.zset

/**
 *  Redis ZADD (파라미터로 받은 값을 ZSet score에 저장)
 *  저장 성공여부를 반환(Boolean)
 * @see io.alloc.cache.handler.zset.RedisZSetSetHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param member        (SpEL) ZSet member
 * @param score         (SpEL) ZSet score
 * @param ttl           만료 시간(초), 0이면 TTL 없음
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisZSetSet(
    val cacheKey: String,
    val member: String,
    val score: String,
    val ttl: Long = 0L
)
