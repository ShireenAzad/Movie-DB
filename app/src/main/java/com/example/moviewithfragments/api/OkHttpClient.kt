package com.example.moviewithfragments.api

import okhttp3.OkHttpClient

class OkHttpClient {
    companion object {
        fun okHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    return@addInterceptor CustomAddInterceptor.addApiKeyToRequests(
                        chain
                    )
                }
                .build()
        }
    }
}