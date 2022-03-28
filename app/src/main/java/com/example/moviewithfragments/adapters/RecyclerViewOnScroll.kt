package com.example.moviewithfragments.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.moviewithfragments.viewmodels.MovieListViewModel

class RecyclerViewOnScroll {
    fun setRecyclerViewScrollListener(
        movieListViewModel: MovieListViewModel,
        recyclerView: RecyclerView?
    ) {
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView!!.canScrollVertically(1) && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    movieListViewModel?.searchNextPage()
                }
            }

        }
        recyclerView?.addOnScrollListener(scrollListener)
    }
}