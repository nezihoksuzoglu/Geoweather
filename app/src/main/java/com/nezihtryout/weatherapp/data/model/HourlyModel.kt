package com.nezihtryout.weatherapp.data.model

import com.nezihtryout.weatherapp.data.model.submodels.WeatherDescModel

data class HourlyModel(
    var dt: Long? = null,
    var temp: String? = null,
    val weather: ArrayList<WeatherDescModel>? = null
)
