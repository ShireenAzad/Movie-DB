package com.example.moviewithfragments.model

import java.util.*

data class MovieData(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    var movieType:String?=""
)