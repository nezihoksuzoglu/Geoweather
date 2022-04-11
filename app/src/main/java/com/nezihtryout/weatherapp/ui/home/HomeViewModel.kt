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
            var lat = 35.0
            var lon = 35.0

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(WeatherServices::class.java)
            val call = service.getCurrentWeatherData(lat, lon, null, appId)
            println("Call= ${call.request()}")
            call.enqueue(object : Callback<WeatherModel> {
                override fun onResponse(
                    call: Call<WeatherModel>,
                    response: Response<WeatherModel>
                ) {
                    Log.e("Call.True",response.message())
                }

                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                    Log.e("Call.False",t.message!!)
                }

            }
            )
        }.start()
    }
}