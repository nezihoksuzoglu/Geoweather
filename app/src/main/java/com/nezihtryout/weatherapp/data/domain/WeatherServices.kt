package com.nezihtryout.weatherapp.data.domain


import com.nezihtryout.weatherapp.data.model.WeatherModel
import com.nezihtryout.weatherapp.data.model.submodels.CityModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServices {

    @GET("data/2.5/onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude : String?,
        @Query("appid") app_id: String,
        @Query("units") units: String
    ): Response<WeatherModel>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") app_id: String
    ): Response<CityModel>
    // https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=minutely,alerts,daily&appid=60b808dc00e5a64209bb6bedf0fc8bb3
}