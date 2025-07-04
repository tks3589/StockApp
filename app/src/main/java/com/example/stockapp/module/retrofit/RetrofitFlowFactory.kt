package com.example.stockapp.module.retrofit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class RetrofitFlowFactory: CallAdapter.Factory() {
    companion object {
        fun create() = RetrofitFlowFactory()
    }
    override fun get(returnType: Type, p1: Array<out Annotation>, p2: Retrofit): CallAdapter<*, *>? {
        if (Flow::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            "Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>"
        }
        val responseType = getParameterUpperBound(0, returnType)
        val rawDeferredType = getRawType(responseType)
        return if (rawDeferredType == Response::class.java) {
            check(responseType is ParameterizedType) {
                "Response must be parameterized as Response<Foo> or Response<out Foo>"
            }
            @Suppress("RemoveExplicitTypeArguments")
            (ResponseCallAdapter<Any>(
        getParameterUpperBound(0, responseType)
    ))
        } else {
            BodyCallAdapter<Any>(responseType)
        }
    }

    private class BodyCallAdapter<T : Any>(private val responseType: Type) : CallAdapter<T, Flow<T>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Flow<T> = flow {
            val newCall = call.takeIf { !it.isExecuted } ?: call.clone()
            val result = newCall.await()
            emit(result)
        }
    }

    private class ResponseCallAdapter<T : Any>(private val responseType: Type) : CallAdapter<T, Flow<Response<T>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Flow<Response<T>> = flow {
            val newCall = call.takeIf { !it.isExecuted } ?: call.clone()
            val result = newCall.awaitResponse()
            emit(result)
        }
    }
}