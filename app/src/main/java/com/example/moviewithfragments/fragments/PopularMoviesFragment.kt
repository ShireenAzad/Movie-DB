package com.example.moviewithfragments.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.enums.MOVIE
import com.example.moviedb.viewmodel.MovieViewModelFactory
import com.example.moviewithfragments.R
import com.example.moviewithfragments.activities.MovieDetailsActivity
import com.example.moviewithfragments.adapters.MovieRecyclerView
import com.example.moviewithfragments.adapters.OnMovieListener
import com.example.moviewithfragments.api.MovieApi
import com.example.moviewithfragments.api.RetrofitClient
import com.example.moviewithfragments.database.MoviesDatabase
import com.example.moviewithfragments.network.isNetworkAvailable
import com.example.moviewithfragments.repository.MovieRepository
import com.example.moviewithfragments.viewmodel.MovieViewModel


class PopularMoviesFragment : Fragment(R.layout.fragment_popular_movies), OnMovieListener {
    val movieRecyclerAdapter = MovieRecyclerView(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewPopularMovies)
        val isNetworkAvailable = requireContext().isNetworkAvailable()
        val moviesViewModel =
            ViewModelProvider(
                requireActivity(),
                MovieViewModelFactory(
                    MovieRepository(
                        RetrofitClient.create().create(MovieApi::class.java),
                        MoviesDatabase.getDatabase(requireContext()).moviesDao(),
                        isNetworkAvailable
                    )
                )
            ).get(
                MovieViewModel::class.java
            )
        moviesViewModel.getMovies()
        recyclerView.adapter = movieRecyclerAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity())
        moviesViewModel.latestMovies.observe(viewLifecycleOwner,{
            movieRecyclerAdapter.updateMovies(it)
        })


    }

    override fun onMovieClick(position: Int) {
        val intent = Intent(activity?.baseContext, MovieDetailsActivity::class.java)
        val movie = movieRecyclerAdapter.getIdOfMovieSelected(position)
        intent.putExtra(MOVIE, movie)
        startActivity(intent)
    }
}