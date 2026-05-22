package io.alloc.cache.annotation.zset

import kotlin.reflect.KClass

/**
 * Redis ZRANGE WITHSCORES (ZSet 전체 조회)
 * @see io.alloc.cache.handler.zset.RedisZSetGetAllHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param memberType    ZSet member 타입
 * @param scoreType     ZSet score 타입
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisZSetGetAll(
    val cacheKey: String,
    val memberType: KClass<*> = String::class,
    val scoreType: KClass<*> = Int::class,
)
