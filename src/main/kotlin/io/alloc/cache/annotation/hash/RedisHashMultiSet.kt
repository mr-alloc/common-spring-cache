package io.alloc.cache.annotation.hash

/**
 * Redis HMSET (원본 메서드 실행 후 모든 엔트리를 Hash 필드에 저장)
 * 반환값은 Map 형식이며 각 요소에 추출된 값이 키가 되어 요소를 반환한다.
 * @see io.alloc.cache.handler.hash.RedisHashMultiSetHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param hashMap       Map 파라미터 변수명
 * @param ttl           만료 시간(초), 0이면 TTL 없음
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisHashMultiSet(
    val cacheKey: String,
    val hashMap: String,
    val ttl: Long = 0L
)
