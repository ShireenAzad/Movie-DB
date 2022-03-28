package com.example.moviewithfragments.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieDB")
data class MovieModelRoom(
    @PrimaryKey
    val movieId: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String?,
    val voteAverage: Float?,
    val primaryYear: Int?
)
