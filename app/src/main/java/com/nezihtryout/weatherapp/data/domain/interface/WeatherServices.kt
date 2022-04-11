package com.nezihtryout.weatherapp.data.domain.`interface`


import com.nezihtryout.weatherapp.data.model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServices {
    @GET("data/2.5/onecall")
    fun getCurrentWeatherData(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("exclude") exclude : Array<String>? = arrayOf("minutely","alerts","daily"),@Query("appid") app_id: String): Call<WeatherModel>
    // https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=minutely,alerts,daily&appid=60b808dc00e5a64209bb6bedf0fc8bb3
}