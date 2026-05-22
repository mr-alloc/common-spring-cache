package io.alloc.cache.handler.hash

import io.alloc.cache.common.CacheHandler
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate

abstract class RedisHashHandler<T : Annotation>(
    private val redisTemplate: RedisTemplate<String, String>
) : CacheHandler<T>() {

    protected val operation: HashOperations<String, String, String> = redisTemplate.opsForHash()
}