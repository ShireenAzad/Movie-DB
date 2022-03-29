package com.example.moviewithfragments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewithfragments.database.MoviesDatabase
import com.example.moviewithfragments.model.Movies
import com.example.moviewithfragments.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(
    private var movieRepository: MovieRepository,
    private var moviesDatabase: MoviesDatabase
) : ViewModel() {
    private val _movies = MutableLiveData<List<Movies>>()
    val movies: LiveData<List<Movies>> = _movies
    fun getMovies(): List<Movies>? {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    val response = movieRepository.getAllMovies()
                    _movies.postValue(response!!)
                    moviesDatabase.moviesDao().insertMovies(response)
                } catch (e: Throwable) {
                    _movies.postValue(moviesDatabase.moviesDao().getMovies())
                }
            }
        }
        return movies.value
    }


    fun getCurrentYearMovies(year: Int): List<Movies>? {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieRepository.searchMoviesFromCurrentYear(year)
            withContext(Dispatchers.Main) {
                try {
                    _movies.value = response!!
                    moviesDatabase.moviesDao().insertMovies(response)

                } catch (e: Throwable) {
                    _movies.postValue(moviesDatabase.moviesDao().getCurrentYearMovies(year))

                }
            }

        }
        return movies.value
    }

    fun getMoviesByKeyWord(query: String): List<Movies>? {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieRepository.searchMovies(query)
            withContext(Dispatchers.Main) {
                try {
                    _movies.value = response!!
                    moviesDatabase.moviesDao().insertMovies(response)

                } catch (e: Throwable) {
                    _movies.postValue(moviesDatabase.moviesDao().getMovies())
                }
            }

        }
        return movies.value
    }

}
