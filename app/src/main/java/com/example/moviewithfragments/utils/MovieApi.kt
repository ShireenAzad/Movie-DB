package com.example.moviewithfragments.utils

import com.example.moviewithfragments.response.MovieSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/3/search/movie")
    open fun searchMovie(
        @Query("api_key") key: String?,
        @Query("query") query: String?,
        @Query("page") page: Int
    ): Call<MovieSearchResponse?>?


    @GET("/3/movie/popular")
    open fun searchForPopularMovies(
        @Query("api_key") key: String?,
    ): Call<MovieSearchResponse?>?


    @GET("3/discover/movie")
    open fun searchMovieByYear(
        @Query("api_key") key: String?,
        @Query("primary_release_year") year: Int?
    ): Call<MovieSearchResponse?>?
}


