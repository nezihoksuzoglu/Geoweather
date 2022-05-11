package com.nezihtryout.weatherapp.data

sealed class CityData<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : CityData<T>(data, null)
    class Error<T>(message: String) : CityData<T>(null, message)
}