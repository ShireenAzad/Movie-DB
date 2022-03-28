package com.example.moviedb.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedb.MoviesDatabase
import com.example.moviedb.repository.MovieRepository

class MovieViewModelFactory constructor(private val movieRepository: MovieRepository,private val moviesDatabase: MoviesDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            MovieViewModel(this.movieRepository,this.moviesDatabase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Exist")
        }
    }
}
