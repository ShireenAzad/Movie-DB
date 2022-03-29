package com.example.moviewithfragments.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviewithfragments.typeconverters.DateConverter
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@TypeConverters(DateConverter::class)
@Parcelize
@Entity(tableName = "movies")
data class Movies(
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @PrimaryKey
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
):Parcelable