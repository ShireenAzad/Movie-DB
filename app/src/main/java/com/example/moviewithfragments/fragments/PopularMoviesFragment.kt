package com.example.moviewithfragments.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviewithfragments.MainActivity
import com.example.moviewithfragments.MovieDetailsActivity
import com.example.moviewithfragments.ObserveDataChange
import com.example.moviewithfragments.R
import com.example.moviewithfragments.adapters.MovieRecyclerView
import com.example.moviewithfragments.adapters.OnMovieListener
import com.example.moviewithfragments.adapters.RecyclerViewOnScroll
import com.example.moviewithfragments.viewmodels.MovieListViewModel


class PopularMoviesFragment : Fragment(R.layout.fragment_popular_movies), OnMovieListener {
    private var movieRecyclerAdapter: MovieRecyclerView? = null
    private var recyclerView: RecyclerView? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieListViewModel =
            ViewModelProvider(requireActivity()).get(MovieListViewModel::class.java)
        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        MainActivity().searchMovieApi("", 0, 1, movieListViewModel)
        movieRecyclerAdapter = MovieRecyclerView(this)
        recyclerView?.adapter = movieRecyclerAdapter
        recyclerView?.layoutManager =
            LinearLayoutManager(requireActivity())
        RecyclerViewOnScroll().setRecyclerViewScrollListener(movieListViewModel, recyclerView)
        ObserveDataChange().observeAnyChange(
            movieListViewModel, viewLifecycleOwner,
            movieRecyclerAdapter!!
        )
        setHasOptionsMenu(true)

    }

    override fun onMovieClick(position: Int) {
        val intent = Intent(activity?.baseContext, MovieDetailsActivity::class.java)
        intent.putExtra("movie", movieRecyclerAdapter?.getIdOfMovieSelected(position))
        startActivity(intent)
    }

    override fun onCategoryClick(category: String?) {
        TODO("Not yet implemented")
    }
}
