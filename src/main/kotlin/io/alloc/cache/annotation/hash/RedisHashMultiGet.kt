package io.alloc.cache.annotation.hash

import kotlin.reflect.KClass

/**
 * Redis HMGET (원본 메서드 실행 후 Hash 필드에 저장)
 * Hash에서 지정된 키에 대한 값을 가져와서 List로 반환
 * @see io.alloc.cache.handler.hash.RedisHashMultiGetHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param hashKey       (SpEL) HASH 키 (컬렉션에서 추출 하는경우 컬렉션 변수명이 items라면, "{items.$.id}")
 * @param valueType     값 타입 클래스
 */
annotation class RedisHashMultiGet(
    val cacheKey: String,
    val hashKey: String,
    val valueType: KClass<*>
)
