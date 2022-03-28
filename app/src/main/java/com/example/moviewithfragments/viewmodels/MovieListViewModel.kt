package com.example.moviewithfragments.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewithfragments.models.MovieModel
import com.example.moviewithfragments.repositories.MovieRepository

class MovieListViewModel : ViewModel() {

    var movieRepository = MovieRepository()
    fun getMovies(): MutableLiveData<List<MovieModel>?> {
        return movieRepository.movies
    }

    fun searchMoviesApi(query: String, year: Int, pageNumber: Int) {
        movieRepository.searchMovieApi(query, year, pageNumber)

    }

    fun searchNextPage() {
        movieRepository.searchNextPage()

    }
}