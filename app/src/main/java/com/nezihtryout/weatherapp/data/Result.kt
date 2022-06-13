package com.nezihtryout.weatherapp.data

sealed class Result<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : Result<T>(data, null)
    class Error<T>(message: String) : Result<T>(null, message)
}