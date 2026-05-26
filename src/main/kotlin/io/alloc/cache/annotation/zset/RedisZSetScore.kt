package io.alloc.cache.annotation.zset

import kotlin.reflect.KClass

/**
 *  Redis ZSCORE (조회 후 없다면 원본 결과 반환)
 * @see io.alloc.cache.handler.zset.RedisZSetScoreHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param member        (SpEL) Redis ZSet 멤버 템플릿
 * @param memberType    ZSet member 타입
 * @param scoreType     ZSet score 타입
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisZSetScore(
    val cacheKey: String,
    val member: String,
    val memberType: KClass<*> = String::class,
    val scoreType: KClass<*> = Int::class,
)
