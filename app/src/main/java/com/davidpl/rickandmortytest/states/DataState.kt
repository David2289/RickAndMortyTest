package com.davidpl.rickandmortytest.states

import com.davidpl.commons.business.ResponseError
import com.davidpl.commons.business.ResponseFailure

sealed class DataState<out T> {

    data class Success<T>(val data: T): DataState<T>()
    data class NoAuth(val data: String): DataState<Nothing>()
    data class ErrorExpected(val data: Any): DataState<Nothing>()
    data class ErrorUnexpected(val data: ResponseError): DataState<Nothing>()
    data class Failure(val data: ResponseFailure): DataState<Nothing>()

}

fun <T> DataState<T>.handleResponseDataState(
    onSuccess: (T) -> Unit,
    onNoAuth: (String) -> Unit = {},
    onErrorExpected: (Any) -> Unit = {},
    onErrorUnexpected: (ResponseError) -> Unit = {},
    onFailure: (ResponseFailure) -> Unit = {}
) {
    return when (this) {
        is DataState.Success -> onSuccess.invoke(this.data)
        is DataState.NoAuth -> onNoAuth.invoke(this.data)
        is DataState.ErrorExpected -> onErrorExpected.invoke(this.data)
        is DataState.ErrorUnexpected -> onErrorUnexpected.invoke(this.data)
        is DataState.Failure -> onFailure.invoke(this.data)
    }
}
