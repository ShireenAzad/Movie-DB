package com.example.moviewithfragments.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        fun create(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(MovieApiUtilities.BASE_URL)
                .client(OkHttpClient.okHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
    }

}