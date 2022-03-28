package com.example.moviewithfragments

import androidx.lifecycle.LifecycleOwner
import com.example.moviewithfragments.adapters.MovieRecyclerView
import com.example.moviewithfragments.viewmodels.MovieListViewModel

class ObserveDataChange {
    fun observeAnyChange(
        movieListViewModel: MovieListViewModel,
        viewLifecycleOwner: LifecycleOwner,
        movieRecyclerAdapter: MovieRecyclerView
    ) {
        movieListViewModel!!.getMovies().observe(
            viewLifecycleOwner
        ) { movieModels ->
            if (movieModels != null) {
                for (movieModel in movieModels) {
                    movieRecyclerAdapter?.setviewModelMovies(movieModels)
                }
            }
        }
        run {}
    }
}