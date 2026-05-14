package io.alloc.cache.common

import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import reactor.core.publisher.Mono
import kotlinx.coroutines.reactor.awaitSingleOrNull
import java.lang.reflect.Method
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.reflect.KClass
import kotlin.reflect.jvm.kotlinFunction

abstract class CacheHandler<T> {
    private val parser: ExpressionParser = SpelExpressionParser()

    /**
     * 캐시 핸들링 메소드
     */
    abstract fun handle(joinPoint: ProceedingJoinPoint, annotation: T): Any?

    fun resolveKey(template: String, parameters: Map<String, Any?>): String {
        val context = StandardEvaluationContext()
        parameters.forEach { (k, v) -> context.setVariable(k, v) }

        return PLACEHOLDER_REGEX.replace(template) { match ->
            val expression = match.groupValues[1]
            runCatching {
                parser.parseExpression("#$expression").getValue(context)?.toString()
            }.getOrNull() ?: parameters[expression]?.toString()
            ?: throw IllegalArgumentException("Cannot resolve key placeholder: {$expression}")
        }
    }


    /**
     * 메서드 파라미터를 이름:값 맵으로 변환
     */
    protected fun getParamMap(method: Method, args: Array<Any?>): Map<String, Any?> {
        val paramNames = method.parameters.map { it.name }
        return paramNames.zip(args).toMap()
    }

    /**
     * 파라미터 맵에서 특정 필드 값 추출
     */
    protected fun resolveParam(paramMap: Map<String, Any?>, fieldName: String): Any {
        return if (PLACEHOLDER_REGEX.containsMatchIn(fieldName)) {
            resolveKey(fieldName, paramMap)
        } else {
            paramMap[fieldName] ?: throw IllegalArgumentException("Parameter not found: '$fieldName'")
        }
    }

    protected fun Method.resolveReturnType(): KClass<*>? {
        // 1. Kotlin/suspend 함수
        kotlinFunction?.returnType?.classifier?.let {
            return it as? KClass<*> ?: Any::class
        }

        // 2. Java 함수 또는 kotlinFunction이 null인 경우
        // primitive/wrapper 모두 kotlin이 처리
        return try {
            returnType.kotlin
        } catch (e: UnsupportedOperationException) {
            // void, array 등 kotlin 변환 불가한 경우
            null
        }
    }

    protected suspend fun ProceedingJoinPoint.proceedWithSuspend(): Any? {
        return when (val result = proceed()) {
            is Mono<*> -> result.awaitSingleOrNull()
            else -> result
        }
    }

    companion object {
        private val PLACEHOLDER_REGEX = Regex("\\{([\\w.]+)}")
    }
}