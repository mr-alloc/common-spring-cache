package io.alloc.cache.annotation.value

/**
 * Redis DEL 캐시 삭제
 * 삭제여부 반환(Boolean)
 * @see io.alloc.cache.handler.value.RedisDeleteHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 *
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisDelete(val cacheKey: String)
