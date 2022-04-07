package com.example.moviewithfragments.api

import com.example.moviewithfragments.BuildConfig
import okhttp3.Interceptor

class CustomAddInterceptor {
    companion object {
        fun addApiKeyToRequests(chain: Interceptor.Chain): okhttp3.Response {
            val API_KEY: String = BuildConfig.API_KEY
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url
            val newUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", API_KEY).build()
            request.url(newUrl)
            return chain.proceed(request.build())
        }
    }
}