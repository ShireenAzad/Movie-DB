package com.example.moviewithfragments.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.with
import com.example.moviedb.adapters.MovieViewHolder
import com.example.moviedb.adapters.OnMovieListener
import com.example.moviewithfragments.R
import com.example.moviewithfragments.model.Movies


class MovieRecyclerView(private val onMovieListener: OnMovieListener) :
    RecyclerView.Adapter<MovieViewHolder>() {
    var movieList = mutableListOf<Movies>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        return MovieViewHolder(view,onMovieListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.title.text = movieList[position].title
        holder.releaseDate.text = movieList[position].releaseDate
        holder.ratingBar.text = (movieList[position].voteAverage?.div(2)).toString()
        with(holder.itemView.context).load( movieList[position].posterPath)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMoviesList(movieList: List<Movies>) {
        this.movieList = movieList.toMutableList()
        notifyDataSetChanged()
    }
    fun getIdOfMovieSelected(position: Int): Movies? {
        if (movieList != null) {
            if (movieList.isNotEmpty()) {
                Log.v("Movie","List position"+movieList[position])
                return movieList[position]
            }
        }
        return null
    }


}