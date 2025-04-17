package com.codekan.weathers.presentation

sealed class ErrorType {
    data object NetworkError : ErrorType()
    data object InvalidCity : ErrorType()
    data class Unknown(val message: String) : ErrorType()
}

sealed class DataState<out T> {
    data object Loading : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error<out T>(val type: ErrorType, val cause: Throwable? = null) : DataState<T>()
}