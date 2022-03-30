package com.example.moviewithfragments.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviewithfragments.model.Movies
import com.example.moviewithfragments.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(
    private var movieRepository: MovieRepository,
) : ViewModel() {
    private val _popularMovies = MutableLiveData<List<Movies>>()
    val popularMovies: LiveData<List<Movies>> = _popularMovies
    private val _latestMovies = MutableLiveData<List<Movies>>()
    val latestMovies: LiveData<List<Movies>> = _latestMovies
    private val _movies = MutableLiveData<List<Movies>>()
    val movies: LiveData<List<Movies>> = _movies
    fun getMovies(): List<Movies>? {
        viewModelScope.launch {
            try {
                val response = movieRepository.getAllMovies()
                _popularMovies.postValue(response!!)

            } catch (e: Throwable) {
                Log.v("Error", "Message" + e.message)
            }
        }

        return popularMovies.value
    }


    fun getCurrentYearMovies(year: Int): List<Movies>? {
        viewModelScope.launch {
            try {
                val response = movieRepository.searchMoviesFromCurrentYear(year)
                _latestMovies.postValue(response!!)

            } catch (e: Throwable) {
                Log.v("Error", "Message" + e.message)

            }


        }
        return latestMovies.value
    }

    fun getMoviesByKeyWord(query: String): List<Movies>? {
        viewModelScope.launch {
            val response = movieRepository.searchMovies(query)

            try {
                _movies.value = response!!

            } catch (e: Throwable) {
                Log.v("Error", "Message" + e.message)

            }


        }
        return movies.value
    }

}
