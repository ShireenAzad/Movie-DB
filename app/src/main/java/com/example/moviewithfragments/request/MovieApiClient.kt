package com.example.moviewithfragments.request

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewithfragments.models.MovieModel
import com.example.moviewithfragments.repositories.MovieRepository
import com.example.moviewithfragments.response.MovieSearchResponse
import com.example.moviewithfragments.utils.Credentials
import retrofit2.Call
import retrofit2.Response

class MovieApiClient : ViewModel() {

    private val viewModelMovies: MutableLiveData<List<MovieModel>?>
    val movies: MutableLiveData<List<MovieModel>?>
        get() = viewModelMovies
    private lateinit var movieRepository: MovieRepository

    companion object {
        var instance: MovieApiClient? = null
            get() {
                if (field == null) {
                    field = MovieApiClient()
                }
                return field
            }
            private set
    }

    init {
        viewModelMovies = MutableLiveData()
    }

    fun searchMoviesApi(query: String, year: Int, pageNumber: Int) {


        if (query.length != 0) {
            val response = getMovies(query, pageNumber)?.execute()
            responseData(response,pageNumber)
        } else {
            if (year == 0) {
                val response = getPopularMovies()?.execute()
                responseData(response,pageNumber)
            } else {
                val response = getLatestMovies(year)?.execute()
                responseData(response,pageNumber)
            }
        }
    }


    fun responseData(response: Response<MovieSearchResponse?>?, pageNumber: Int)
    {
        if (response!!.code() == 200) {
            val list = response.body()!!.movies as java.util.ArrayList<MovieModel>?
            if (pageNumber == 1) {

                if (list != null) {
                    //viewModelScope
                    //movieRepository.insertData(list)
                }
                viewModelMovies?.postValue(list as List<MovieModel>?)
            } else {

                val currentMovies =
                    viewModelMovies.value as java.util.ArrayList<MovieModel>?

                currentMovies?.clear()
                currentMovies?.addAll(list!!)
                if (currentMovies != null) {
                   // movieRepository.insertData(currentMovies)
                }
                viewModelMovies.postValue(currentMovies!!)

            }

        } else {
            val error = response.errorBody().toString()
            Log.v("Tag", "Error" + error)
            viewModelMovies.postValue(null)
        }

    }

    //Search Method
    private fun getMovies(query: String, pageNumber: Int): Call<MovieSearchResponse?>? {

        val service = Service()
        val credentials = Credentials()
        return service.getMovieApi().searchMovie(
            credentials.API_KEY, query, pageNumber
        )
    }

    private fun getPopularMovies(): Call<MovieSearchResponse?>? {
        val service = Service()
        val credentials = Credentials()
        return service.getMovieApi().searchForPopularMovies(
            credentials.API_KEY
        )
    }

    private fun getLatestMovies(year: Int): Call<MovieSearchResponse?>? {
        val service = Service()
        val credentials = Credentials()
        return service.getMovieApi().searchMovieByYear(
            credentials.API_KEY,
            year
        )
    }
//
//    private fun cancelRequest() {
//        Log.v("Tag", "Cancel retrofit Search Request")
//        cancelRequest = true
//        return cancelRequest
//    }
}

