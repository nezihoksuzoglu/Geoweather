package com.nezihtryout.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nezihtryout.weatherapp.data.domain.repository.HomeRepository
import com.nezihtryout.weatherapp.data.model.WeatherModel


class HomeViewModel(application : Application) : AndroidViewModel(application){
    private val repository  = HomeRepository(application)
    val weather: LiveData<WeatherModel>
    init {
        this.weather = repository.weather
    }
    fun readWeatherFromAPI() = repository.readWeatherFromAPI()
}