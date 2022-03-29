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
    private val _popularMovies = MutableLiveData<List<Movies>>()
    val popularMovies: LiveData<List<Movies>> = _popularMovies
    private val _latestMovies = MutableLiveData<List<Movies>>()
    val latestMovies: LiveData<List<Movies>> = _latestMovies
    private val _movies = MutableLiveData<List<Movies>>()
    val movies: LiveData<List<Movies>> = _movies
    fun getMovies(): List<Movies>? {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    val response = movieRepository.getAllMovies()
                    _popularMovies.postValue(response!!)
                    moviesDatabase.moviesDao().insertMovies(response)
                } catch (e: Throwable) {
                    _popularMovies.postValue(moviesDatabase.moviesDao().getMovies())
                }
            }
        }
        return popularMovies.value
    }


    fun getCurrentYearMovies(year: Int): List<Movies>? {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    val response = movieRepository.searchMoviesFromCurrentYear(year)
                    _latestMovies.postValue(response!!)
                    moviesDatabase.moviesDao().insertMovies(response)

                } catch (e: Throwable) {
                    _latestMovies.postValue(moviesDatabase.moviesDao().getCurrentYearMovies(year))

                }
            }

        }
        return latestMovies.value
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
