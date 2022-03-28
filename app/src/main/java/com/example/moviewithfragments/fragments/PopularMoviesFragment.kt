package com.example.moviewithfragments.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import com.example.moviewithfragments.database.MoviesDatabase
import com.example.moviewithfragments.repository.MovieRepository
import com.example.moviewithfragments.viewmodel.MovieViewModel


class PopularMoviesFragment : Fragment(R.layout.fragment_popular_movies), OnMovieListener {
    val movieRecyclerAdapter = MovieRecyclerView(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val moviesViewModel =
            ViewModelProvider(
                requireActivity(),
                MovieViewModelFactory(
                    MovieRepository(
                        MovieApi.create(),
                    ),
                    MoviesDatabase.getDatabase(requireContext())
                )
            ).get(
                MovieViewModel::class.java
            )
        moviesViewModel.getMovies()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.adapter = movieRecyclerAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity())
        moviesViewModel.movies.observe(viewLifecycleOwner, Observer {
            movieRecyclerAdapter.setMoviesList(it)
        })

    }


    override fun onMovieClick(position: Int) {
        val intent = Intent(activity?.baseContext, MovieDetailsActivity::class.java)
        val movie = movieRecyclerAdapter.getIdOfMovieSelected(position)
        intent.putExtra(MOVIE, movie)
        startActivity(intent)
    }

    override fun onCategoryClick(category: String?) {
        TODO("Not yet implemented")
    }
}
