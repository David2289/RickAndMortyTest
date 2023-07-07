package com.davidpl.rickandmortytest.datasource.remote

import com.davidpl.commons.business.ResponseError
import com.davidpl.commons.business.ResponseFailure
import com.davidpl.rickandmortytest.states.DataState
import retrofit2.Response

suspend fun <A, D> handleResponse(
    call: suspend () -> Response<A>,
    toDomain: A.() -> D
): DataState<D> {
    return try {
        val response = call.invoke()
        when {
            response. isSuccessful -> {
                when (val body = response.body()) {
                    null -> {
                        val responseFailure = ResponseFailure(exception = NullPointerException())
                        DataState.Failure(responseFailure)
                    }
                    else -> {
                        val domain = body.toDomain()
                        DataState.Success(domain)
                    }
                }
            }
            else -> {
                val responseError = ResponseError(
                    response.raw().code,
                    response.raw().request.method,
                    response.raw().request.url.toString(),
                    response.raw().request.headers.toString(),
                    response.raw().message,
                    response.errorBody()?.string()
                )
                DataState.ErrorUnexpected(responseError)
            }
        }
    }
    catch (exception: Exception) {
        val responseFailure = ResponseFailure(exception = exception)
        DataState.Failure(responseFailure)
    }
}
