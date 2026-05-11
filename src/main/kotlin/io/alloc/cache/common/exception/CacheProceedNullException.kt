package io.alloc.cache.common.exception

class CacheProceedNullException(
    methodName: String,
) : RuntimeException("Proceed result of method \\\"$methodName\\\" was null")