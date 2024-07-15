package com.glacierpower.rickandmorty.util

sealed class ResultState<T> {
    class Success<T>(val data: T) : ResultState<T>()
    class Error<T>(message: String, data: T? = null) : ResultState<T>()
    class Loading<T> : ResultState<T>()
}