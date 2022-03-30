package com.example.moviewithfragments.repository

import androidx.lifecycle.MutableLiveData
import com.example.moviewithfragments.api.MovieApi
import com.example.moviewithfragments.api.MovieApiUtilities
import com.example.moviewithfragments.database.MoviesDatabase
import com.example.moviewithfragments.model.MovieData
import com.example.moviewithfragments.model.MovieModel
import com.example.moviewithfragments.model.Movies
import com.example.moviewithfragments.model.ResponseResults
import com.example.moviewithfragments.network.NetworkConnection

class MovieRepository(
    private val movieApi: MovieApi,
    private val moviesDatabase: MoviesDatabase
) {
    var responseResult: MutableLiveData<ResponseResults<List<MovieData>>> = MutableLiveData()
    private var networkConnection: NetworkConnection? = null

    suspend fun getAllMovies(): ResponseResults<List<MovieData>> {
        if (networkConnection?.haveNetworkConnection()==true) {

            try {
                val response = movieApi.getMovies()
                if (response.isSuccessful) {
                    val dbData = response.body()?.results?.let { convertResponseToEntity(it) }
                    moviesDatabase.moviesDao().insertMovies(dbData)
                    val data = response.body()?.results?.let { convertDTOIntoUIModel(it) }
                    return ResponseResults.Success(data)

                }
                  return  ResponseResults.Failure(response.message())


            } catch (e: Exception) {
               return  ResponseResults.Failure(e.message.toString())
            }
        }
            val popularMovies = getPopularMovies()
        return ResponseResults.Success(popularMovies)

    }


    suspend fun searchMoviesFromCurrentYear(year: Int): ResponseResults<List<MovieData>>  {
        if (networkConnection?.haveNetworkConnection()==true) {

            try {
                val response = movieApi.searchMovieByYear(year)
                if (response.isSuccessful) {
                    val dbData = response.body()?.results?.let { convertResponseToEntity(it) }
                    moviesDatabase.moviesDao().insertMovies(dbData)
                    val data = response.body()?.results?.let { convertDTOIntoUIModel(it) }
                    return ResponseResults.Success(data)

                }
                return  ResponseResults.Failure(response.message())


            } catch (e: Exception) {
                return  ResponseResults.Failure(e.message.toString())
            }
        }
        val popularMovies = getCurrentYearMovies(year)
        return ResponseResults.Success(popularMovies)
    }

    suspend fun searchMovies(query: String): ResponseResults<List<MovieData>> {
        if (networkConnection?.haveNetworkConnection()==true) {

            try {
                val response = movieApi.searchMovie(query)
                if (response.isSuccessful) {
                    val dbData = response.body()?.results?.let { convertResponseToEntity(it) }
                    moviesDatabase.moviesDao().insertMovies(dbData)
                    val data = response.body()?.results?.let { convertDTOIntoUIModel(it) }
                    return ResponseResults.Success(data)

                }
                return  ResponseResults.Failure(response.message())


            } catch (e: Exception) {
                return  ResponseResults.Failure(e.message.toString())
            }
        }
        val popularMovies = getPopularMovies()
        return ResponseResults.Success(popularMovies)

    }

     fun getPopularMovies(): List<MovieData> {
        val movies = moviesDatabase.moviesDao().getMovies()
        return movies
    }

   fun getCurrentYearMovies(year: Int): List<MovieData> {
        return moviesDatabase.moviesDao().getMoviesByReleaseYear(year)

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
