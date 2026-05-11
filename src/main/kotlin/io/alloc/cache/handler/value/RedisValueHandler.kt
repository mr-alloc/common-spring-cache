package io.alloc.cache.handler.value

import io.alloc.cache.common.CacheHandler
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations

abstract class RedisValueHandler<T>(
    private val redisTemplate: RedisTemplate<String, String>,
) : CacheHandler<T>() {

    protected val operation: ValueOperations<String, String> = redisTemplate.opsForValue()
}