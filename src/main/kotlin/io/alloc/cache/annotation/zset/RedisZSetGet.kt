package io.alloc.cache.annotation.zset

import kotlin.reflect.KClass

/**
 *  Redis ZADD (파라미터로 받은 값을 ZSet score에 저장)
 *  저장 성공여부를 반환(Boolean)
 * @see io.alloc.cache.handler.zset.RedisZSetSetHandler
 *
 * @param cacheKey      (SpEL) Redis 키 템플릿
 * @param member        (SpEL) Redis ZSet 멤버 템플릿
 * @param memberType    ZSet member 타입
 * @param scoreType     ZSet score 타입
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisZSetGet(
    val cacheKey: String,
    val member: String,
    val memberType: KClass<*> = String::class,
    val scoreType: KClass<*> = Int::class,
)
