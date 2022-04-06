package com.example.moviewithfragments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviewithfragments.model.MovieData
import com.example.moviewithfragments.model.ResponseResults
import com.example.moviewithfragments.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(
    private var movieRepository: MovieRepository,
) : ViewModel() {
    private val _popularMovies = MutableLiveData<List<MovieData>?>()
    val popularMovies: MutableLiveData<List<MovieData>?> = _popularMovies
    private val _latestMovies = MutableLiveData<List<MovieData>>()
    val latestMovies: LiveData<List<MovieData>> = _latestMovies
    private val _movies = MutableLiveData<List<MovieData>>()
    val movies: LiveData<List<MovieData>> = _movies
    private val _error = MutableLiveData<String?>()
    val error: MutableLiveData<String?> = _error
    fun getMovies() {
        viewModelScope.launch {
            val response = movieRepository.getAllMovies()
            when (response) {
                is ResponseResults.Success -> _popularMovies.postValue(response.data)
                is ResponseResults.Failure -> _error.postValue(response.error)
            }
        }
    }


    fun getCurrentYearMovies(year: Int) {
        viewModelScope.launch {

            val response = movieRepository.searchMoviesFromCurrentYear(year)
            when (response) {
                is ResponseResults.Success -> _latestMovies.postValue(response.data!!)
                is ResponseResults.Failure -> _error.postValue(response.error!!)
            }
        }
    }

    fun getMoviesByKeyWord(query: String) {
        viewModelScope.launch {
            val response = movieRepository.searchMovies(query)
            when (response) {
                is ResponseResults.Success -> _movies.postValue(response.data!!)
                is ResponseResults.Failure -> _error.postValue(response.error!!)
            }
        }
    }
}


