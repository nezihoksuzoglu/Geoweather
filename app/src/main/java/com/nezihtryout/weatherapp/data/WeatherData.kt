package com.nezihtryout.weatherapp.data

sealed class WeatherData<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : WeatherData<T>(data, null)
    class Error<T>(message: String) : WeatherData<T>(null, message)
}