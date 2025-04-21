package com.codekan.weathers.presentation

// Hata türleri için sealed class (daha iyi hata yönetimi)
sealed class ErrorType {
    data object NetworkError : ErrorType()
    data object StaleData : ErrorType()
    data object DatabaseError : ErrorType()
    data class Unknown(val message: String) : ErrorType()
}

// DataState sınıfı
sealed class DataState<out T> {
    data object Loading : DataState<Nothing>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val errorType: ErrorType, val message: String? = null) : DataState<Nothing>()
}