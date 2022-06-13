package com.nezihtryout.weatherapp.data.model

import com.nezihtryout.weatherapp.data.model.submodels.WeatherDescModel

data class CurrentModel(
    val dt: Float? = null,
    var temp: String? = null,
    val weather: ArrayList<WeatherDescModel>? = null
)
