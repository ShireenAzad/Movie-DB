package com.example.moviewithfragments.response

import com.example.moviewithfragments.models.MovieModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieSearchResponse {
    @SerializedName("total_results")
    @Expose
    private val total_count = 0

    @SerializedName("results")
    @Expose
    val movies: List<MovieModel?>? = null

    override fun toString(): String {
        return "MovieSearchResponsey{" +
                "total_count=" + total_count +
                ", movies=" + movies +
                '}'
    }

}

