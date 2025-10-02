package com.mcshr.moviebrowser.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =  chain.request().newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}