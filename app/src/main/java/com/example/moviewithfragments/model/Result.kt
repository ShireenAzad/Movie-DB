package com.example.moviewithfragments.model

import com.google.gson.annotations.SerializedName


data class Result(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movies>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)