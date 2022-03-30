package com.example.moviewithfragments.model

sealed class ResponseResults<out T> {
    data class Success<out T>(val movieData: T? = null) : ResponseResults<T>()
    data class Loading(val nothing: Nothing? = null) : ResponseResults<Nothing>()
    data class Error(val message: String? = null) : ResponseResults<Nothing>()
    data class Exception(val message: String? = null) : ResponseResults<Nothing>()
}