package com.example.moviewithfragments.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movies(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    @PrimaryKey
    val title: String,
    val voteAverage: Double
)