package com.example.moviewithfragments.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewithfragments.MoviesDatabase
import com.example.moviewithfragments.model.Movies
import com.example.moviewithfragments.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MovieViewModel(
    private var movieRepository: MovieRepository,
    private var moviesDatabase: MoviesDatabase
) : ViewModel() {
    private val _movies = MutableLiveData<List<Movies>>()
    val movies: LiveData<List<Movies>> = _movies
    fun getMovies(): List<Movies>? {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieRepository.getAllMovies()
            withContext(Dispatchers.Main) {
                try {
                    _movies.postValue(response!!)
                    moviesDatabase.moviesDao().insertMovies(response)
                } catch (e: Throwable) {
                    moviesDatabase.moviesDao().getMovies()
                    Log.v("Ooops", "Data not found" + e.message)
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
                    moviesDatabase.moviesDao().getMovies()
                    Log.v("Ooops", "Data not found")
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
                    if (response != null) {
                        if (response.isNotEmpty()) {
                            _movies.value = response!!
                            moviesDatabase.moviesDao().insertMovies(response)
                            Log.v("Popular", "List Check" + movies.value)
                            //Do something with response e.g show to the UI.
                        } else {
                            Log.v("Error", "Error: ${response}")
                        }
                    }
                } catch (e: HttpException) {
                    Log.v("Exception", "No  ${e.message}")
                } catch (e: Throwable) {

                    Log.v("Ooops", "Data not found")
                }
            }

        }
        return movies.value
    }

}
