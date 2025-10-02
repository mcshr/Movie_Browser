package com.mcshr.moviebrowser.domain.wrappers

sealed class DomainError {
    class NetworkError(): DomainError()
    class ServerError(): DomainError()
    class UnknownError(val throwable: Throwable): DomainError()
}