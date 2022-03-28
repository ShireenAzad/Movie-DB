package com.example.moviewithfragments.repositories

import androidx.lifecycle.MutableLiveData
import com.example.moviewithfragments.dao.MovieDao
import com.example.moviewithfragments.database.MoviesDatabase
import com.example.moviewithfragments.models.MovieModel
import com.example.moviewithfragments.request.MovieApiClient

class MovieRepository() {
    private var mQuery: String? = null
    private var mPageNumber = 0
    private var mYear = 0
    val movieApiClient: MovieApiClient
    val movies: MutableLiveData<List<MovieModel>?>
        get() = movieApiClient.movies
private lateinit var movieDao: MovieDao
private lateinit var moviesDatabase:MoviesDatabase
    companion object {
        var instance: MovieRepository? = null
            get() {
                if (field == null) {
                    field = MovieRepository()
                }
                return field
            }
            private set
    }

    init {
        movieApiClient = MovieApiClient.instance!!
    }

    fun searchMovieApi(query: String, year: Int, pageNumber: Int) {
        mQuery = query
        mYear = year
        mPageNumber = pageNumber
        movieApiClient.searchMoviesApi(query, year, pageNumber)

    }


    fun searchNextPage() {
        mQuery?.let { searchMovieApi(it, mYear, mPageNumber + 1) }
    }

    suspend fun insertData(list: ArrayList<MovieModel>)
    {
        for(movie in list)
        moviesDatabase.movieDao().insertData(movie)
    }}