package com.example.moviewithfragments.repository

import com.example.moviewithfragments.api.MovieApi
import com.example.moviewithfragments.api.MovieApiUtilities
import com.example.moviewithfragments.model.Movies

class MovieRepository(
    private val movieApi: MovieApi
) {
    suspend fun getAllMovies(): List<Movies>? {
        val movies = movieApi.getMovies()
        return movies.body()?.results?.let { convertDTOIntoUIModel(it) }
    }

    suspend fun searchMoviesFromCurrentYear(year: Int): List<Movies>? {
        val movies = movieApi.searchMovieByYear(year)
        return movies.body()?.results?.let { convertDTOIntoUIModel(it) }


    }

    suspend fun searchMovies(query: String): List<Movies>? {
        val movies = movieApi.searchMovie(query, 1)
        return movies.body()?.results?.let { convertDTOIntoUIModel(it) }

    }

    private fun convertDTOIntoUIModel(movies: List<Movies>): List<Movies> {
        return movies.map {
            Movies(it.id,  it.overview,MovieApiUtilities.IMAGE_URI+it.posterPath, it.releaseDate, it.title, it.voteAverage)
        }
    }
}
