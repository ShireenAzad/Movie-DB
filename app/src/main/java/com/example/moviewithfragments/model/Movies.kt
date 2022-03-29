package com.example.moviewithfragments.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//Entity class for db model
@Entity(tableName = "movies")
data class Movies(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: Date,
    @PrimaryKey
    val title: String,
    val voteAverage: Double,
    @ColumnInfo
    var movieType: String? = ""
)