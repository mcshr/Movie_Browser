package com.mcshr.moviebrowser.domain.wrappers

sealed class NetworkResult<out T> {
    class Success<T>(val data:T): NetworkResult<T>()
    class Error(val error: DomainError):NetworkResult<Nothing>()
    class Loading:NetworkResult<Nothing>()
}