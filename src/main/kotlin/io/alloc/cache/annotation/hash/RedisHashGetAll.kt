package io.alloc.cache.annotation.hash

import kotlin.reflect.KClass

/**
 * Redis HGETALL (조회 후 없다면 원본 메서드 실행 후 Hash 필드에 저장)
 * Hash 전체를 가져와 Map<String, T> 로 반환 (캐시 미스 시 원본 실행 후 HMSET)
 * @see io.alloc.cache.handler.hash.RedisHashGetAllHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param valueType     값 타입 클래스
 * @param ttl           만료 시간(초), 0이면 TTL 없음
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisHashGetAll(
    val cacheKey: String,
    val valueType: KClass<*>,
    val ttl: Long = 0L,
)