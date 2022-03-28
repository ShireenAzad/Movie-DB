package com.example.moviewithfragments.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.moviewithfragments.databinding.FragmentLatestMoviesBinding
import com.example.moviewithfragments.viewmodels.MovieListViewModel
import java.util.*

class LatestMoviesFragment : Fragment(), OnMovieListener {
    private var movieRecyclerAdapter: MovieRecyclerView? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var latestMoviesBinding: FragmentLatestMoviesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLatestMoviesBinding.inflate(inflater, container, false)
        latestMoviesBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieListViewModel =
            ViewModelProvider(requireActivity()).get(MovieListViewModel::class.java)
        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        MainActivity().searchMovieApi(
            "",
            Calendar.getInstance().get(Calendar.YEAR),
            1,
            movieListViewModel
        )
        movieRecyclerAdapter = MovieRecyclerView(this)
        recyclerView?.adapter = movieRecyclerAdapter
        recyclerView?.layoutManager =
            LinearLayoutManager(requireActivity())
        RecyclerViewOnScroll().setRecyclerViewScrollListener(movieListViewModel, recyclerView)
        ObserveDataChange().observeAnyChange(
            movieListViewModel, viewLifecycleOwner,
            movieRecyclerAdapter!!
        )
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