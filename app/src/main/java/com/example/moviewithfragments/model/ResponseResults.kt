package com.example.moviewithfragments.model

sealed class ResponseResults<T>() {
    class Success<T>(val data: T? = null) : ResponseResults<T>()
    class Failure<T>(val error: String) : ResponseResults<T>()
    class Loading<T>() : ResponseResults<T>()
}