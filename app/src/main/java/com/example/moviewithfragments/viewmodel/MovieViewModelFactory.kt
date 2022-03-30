package com.example.moviedb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviewithfragments.repository.MovieRepository
import com.example.moviewithfragments.viewmodel.MovieViewModel

class MovieViewModelFactory constructor(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            MovieViewModel(this.movieRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Exist")
        }
    }
}
