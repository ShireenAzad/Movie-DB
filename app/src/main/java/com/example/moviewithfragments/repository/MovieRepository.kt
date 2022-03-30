package com.example.moviewithfragments.repository

import com.example.moviewithfragments.api.MovieApi
import com.example.moviewithfragments.api.MovieApiUtilities
import com.example.moviewithfragments.database.MoviesDatabase
import com.example.moviewithfragments.model.MovieData
import com.example.moviewithfragments.model.MovieModel
import com.example.moviewithfragments.model.Movies
import com.example.moviewithfragments.network.NetworkConnection

class MovieRepository(
    private val movieApi: MovieApi,
    private val moviesDatabase: MoviesDatabase
) {
    private lateinit var networkConnection:NetworkConnection
    suspend fun getAllMovies(): List<Movies>? {
        val movies = movieApi.getMovies()
        val results = movies.body()?.results
        if(networkConnection.haveNetworkConnection()){
            return movies.body()?.results?.let { convertDTOIntoUIModel(it) }
        }
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

    private fun convertDTOIntoUIModel(movies: List<MovieModel>): List<Movies> {
        return movies.map {
            Movies(
                it.id,
                it.overview,
                MovieApiUtilities.IMAGE_URI + it.posterPath,
                it.releaseDate,
                it.title,
                it.voteAverage
            )
        }
    }

    private fun convertEntityDTOIntoUIModel(movies: List<Movies>): List<MovieData> {
        return movies.map {
            MovieData(
                it.id,
                it.overview,
                MovieApiUtilities.IMAGE_URI + it.posterPath,
                it.releaseDate,
                it.title,
                it.voteAverage
            )
        }
    }

}
