package io.alloc.cache.annotation.hash

/**
 * Redis HDEL 키 삭제
 * @see io.alloc.cache.handler.hash.RedisHashDeleteHandler
 * 사용되는 함수의 반환타입은 Long이여아 한다
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param hashKey       (SpEL) HASH 키 (컬렉션에서 추출 하는경우 컬렉션 변수명이 items라면, "{items.$.id}")
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisHashDelete(
    val cacheKey: String,
    val hashKey: String
)
