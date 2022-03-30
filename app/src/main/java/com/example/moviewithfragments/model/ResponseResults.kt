package com.example.moviewithfragments.model

sealed class ResponseResults<T>(
    val data : T? = null,
    val error : String? = null
){
    class Success<T> (data : T?=null) : ResponseResults<T>(data = data)
    class Failure<T> (error : String) : ResponseResults<T>(error = error)
    class Loading<T> () : ResponseResults<T>()
}