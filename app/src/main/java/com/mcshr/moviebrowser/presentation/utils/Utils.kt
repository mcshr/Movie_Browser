package com.mcshr.moviebrowser.presentation.utils

import android.content.Context
import com.mcshr.moviebrowser.R
import com.mcshr.moviebrowser.domain.wrappers.DomainError

fun DomainError.getErrorMessage(context: Context): String {
    return when (this) {
        is DomainError.NetworkError -> context.getString(R.string.error_network)
        is DomainError.ServerError -> context.getString(R.string.error_server)
        is DomainError.UnknownError -> context.getString(R.string.error_unknown)
    }
}
