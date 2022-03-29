package com.example.moviewithfragments

import androidx.recyclerview.widget.DiffUtil
import com.example.moviewithfragments.model.Movies

class Utils(private val oldMovies: List<Movies>, private val newMovies: List<Movies>) : DiffUtil.Callback() {
  override fun getOldListSize(): Int {
        return oldMovies.size
    }

    override fun getNewListSize(): Int {
        return newMovies.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMovies.get(oldItemPosition).title == newMovies.get(
            newItemPosition
        ).title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMovies[oldItemPosition].title.equals(newMovies[newItemPosition].title)
    }

}