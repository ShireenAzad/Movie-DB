package com.example.moviewithfragments.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.with
import com.example.moviewithfragments.R
import com.example.moviewithfragments.Utils
import com.example.moviewithfragments.model.MovieData


class MovieRecyclerView(private val onMovieListener: OnMovieListener) :
    RecyclerView.Adapter<MovieViewHolder>() {
    var movieList = ArrayList<MovieData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        return MovieViewHolder(view,onMovieListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.title.text = movieList[position].title
        holder.releaseDate.text = movieList[position].releaseDate.toString()
        holder.ratingBar.text = (movieList[position].voteAverage?.div(2)).toString()
        with(holder.itemView.context).load( movieList[position].posterPath)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun getIdOfMovieSelected(position: Int): MovieData? {
        if (movieList != null) {
            if (movieList.isNotEmpty()) {
                Log.v("Movie","List position"+movieList[position])
                return movieList[position]
            }
        }
        return null
    }
    fun updateMovies(movies: List<MovieData>) {
        val diffCallback =Utils(this.movieList, movies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.movieList.clear()
        this.movieList.addAll(movies)
        diffResult.dispatchUpdatesTo(this)
    }

}
