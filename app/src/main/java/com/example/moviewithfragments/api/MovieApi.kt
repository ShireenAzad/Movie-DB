package com.example.moviewithfragments.api


import com.example.moviewithfragments.model.Result
import retrofit2.Response
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
        @Query("query") query: String?
    ): Response<Result>

}