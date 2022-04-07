package com.example.moviewithfragments.repository

import android.app.Application
import com.example.moviewithfragments.api.MovieApi
import com.example.moviewithfragments.api.MovieApiUtilities
import com.example.moviewithfragments.dao.MoviesDao
import com.example.moviewithfragments.model.MovieData
import com.example.moviewithfragments.model.MovieModel
import com.example.moviewithfragments.model.Movies
import com.example.moviewithfragments.model.ResponseResults

class MovieRepository(
    private val movieApi: MovieApi,
    private val moviesDao: MoviesDao,
    private val isNetworkAvailable: Boolean
) : Application() {
    suspend fun getAllMovies(): ResponseResults<List<MovieData>> {
        if (isNetworkAvailable) {
            try {
                val response = movieApi.getMovies()
                if (response.isSuccessful) {
                    val dbData = response.body()?.results?.let { convertResponseToEntity(it) }
                    moviesDao.insertMovies(dbData)
                    val data = response.body()?.results?.let { convertDTOIntoUIModel(it) }
                    return ResponseResults.Success(data)

                }
                return ResponseResults.Failure(response.message())


            } catch (e: Exception) {
                return ResponseResults.Failure(e.message.toString())
            }
        }
        val popularMovies = getPopularMovies()
        return ResponseResults.Success(popularMovies)

    }


    suspend fun searchMoviesFromCurrentYear(year: Int): ResponseResults<List<MovieData>> {
        if (isNetworkAvailable) {
            try {
                val response = movieApi.searchMovieByYear(year)
                if (response.isSuccessful) {
                    val dbData = response.body()?.results?.let { convertResponseToEntity(it) }
                    moviesDao.insertMovies(dbData)
                    val data = response.body()?.results?.let { convertDTOIntoUIModel(it) }
                    return ResponseResults.Success(data)

                }
                return ResponseResults.Failure(response.message())


            } catch (e: Exception) {
                return ResponseResults.Failure(e.message.toString())
            }
        }
        val latestMovies = getCurrentYearMovies(year)
        return ResponseResults.Success(latestMovies)
    }

    suspend fun searchMovies(query: String): ResponseResults<List<MovieData>> {

        if (isNetworkAvailable) {

            try {
                val response = movieApi.searchMovie(query)
                if (response.isSuccessful) {
                    val dbData = response.body()?.results?.let { convertResponseToEntity(it) }
                    moviesDao.insertMovies(dbData)
                    val data = response.body()?.results?.let { convertDTOIntoUIModel(it) }
                    return ResponseResults.Success(data)

                }
                return ResponseResults.Failure(response.message())


            } catch (e: Exception) {
                return ResponseResults.Failure(e.message.toString())
            }
        }
        val popularMovies =getMoviesByKeyWord(query)
        return ResponseResults.Success(popularMovies)

    }

    private fun getPopularMovies(): List<MovieData> {
       return moviesDao.getMovies()
    }

    private fun getCurrentYearMovies(year: Int): List<MovieData> {
        return moviesDao.getMoviesByReleaseYear(year)

    }
    private fun getMoviesByKeyWord(query: String):List<MovieData>{
        return moviesDao.getMoviesByKeyWord(query)
    }

    private fun convertResponseToEntity(movies: List<MovieModel>): List<Movies> {
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

    private fun convertDTOIntoUIModel(movies: List<MovieModel>): List<MovieData> {

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
