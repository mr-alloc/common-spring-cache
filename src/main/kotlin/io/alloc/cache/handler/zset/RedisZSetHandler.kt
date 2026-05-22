package io.alloc.cache.handler.zset

import io.alloc.cache.common.CacheHandler
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ZSetOperations

abstract class RedisZSetHandler<T : Annotation>(
    private val redisTemplate: RedisTemplate<String, String>
) : CacheHandler<T>() {

    protected val operation: ZSetOperations<String, String> = redisTemplate.opsForZSet()
}