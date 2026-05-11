package io.alloc.cache.annotation.value

/**
 * Redis GET (원본 메서드 실행 무시 및 조회)
 * @see io.alloc.cache.handler.value.RedisGetHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 *
 * @see io.alloc.cache.common.exception.CacheProceedNullException
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisFetch(val cacheKey: String)
