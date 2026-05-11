package io.alloc.cache.annotation.value

/**
 * Redis SET, GET (원본 메서드 실행 후 새로고침)
 * @see io.alloc.cache.handler.value.RedisRefreshHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param ttl           만료 시간(초), 0이면 TTL 없음
 * @param nullable      Null 허용(false: 메소드 결과가 null인 경우 예외 발생)
 *
 * @see io.alloc.cache.common.exception.CacheProceedNullException
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisRefresh(
    val cacheKey: String,
    val ttl: Long = 0L,
    val nullable: Boolean = true
)
