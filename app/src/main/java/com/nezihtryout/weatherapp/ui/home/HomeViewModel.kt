package com.nezihtryout.weatherapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nezihtryout.weatherapp.data.domain.`interface`.WeatherServices
import com.nezihtryout.weatherapp.data.model.WeatherModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class HomeViewModel : ViewModel(){

    fun APIRead(){
        Thread{
            // TEMP
            var baseUrl : String = "https://api.openweathermap.org/"
            var appId : String = "60b808dc00e5a64209bb6bedf0fc8bb3"
            var lat = 41.0424606
            var lon = 29.0085191

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(WeatherServices::class.java)
            val oneCallAPIFormat = "minutely,alerts"
            val call = service.getCurrentWeatherData(lat, lon, oneCallAPIFormat , appId)
            println("Call= ${call.request()}")
            call.enqueue(object : Callback<WeatherModel> {
                override fun onResponse(
                    call: Call<WeatherModel>,
                    response: Response<WeatherModel>
                ) {
                    Log.e("Call.True",response.message())
                    val weatherResponse = response.body()
                    val stringBuilder = "Weather: " +
                            weatherResponse?.current?.weather!![0]?.main+
                            "\n" +
                            "Place: " +
                            weatherResponse?.timezone +
                            "\n" +
                            "Lat&Lon: " +
                            weatherResponse?.lat + "," + weatherResponse?.lon

                    println(stringBuilder)
                }

                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                    Log.e("Call.False",t.message!!)
                }

            }
            )
        }.start()
    }
}