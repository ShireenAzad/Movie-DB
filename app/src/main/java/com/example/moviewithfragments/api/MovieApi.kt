package com.example.moviewithfragments.api


import com.example.moviewithfragments.BuildConfig
import com.example.moviewithfragments.model.Result
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getMovies(): Response<Result>

    @GET("discover/movie")
    suspend fun searchMovieByYear(
        @Query("primary_release_year") year: Int?
    ): Response<Result>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String?,
        @Query("page") page: Int
    ):Response<Result>

    companion object {

        fun create(): MovieApi {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain -> return@addInterceptor addApiKeyToRequests(chain) }
                .build()

            return Retrofit.Builder()
                .baseUrl(MovieApiUtilities.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi::class.java)
        }

        private fun addApiKeyToRequests(chain: Interceptor.Chain): okhttp3.Response {
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