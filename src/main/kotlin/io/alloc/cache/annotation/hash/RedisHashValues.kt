package io.alloc.cache.annotation.hash

import kotlin.reflect.KClass

/**
 * Redis HVALS (해시 값 목록 조회)
 * 반환 타입은 List이며, 원본 메서드는 실행하지 않는다
 * @see io.alloc.cache.handler.hash.RedisHashValuesHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisHashValues(
    val cacheKey: String,
    val valueType: KClass<*>
)
