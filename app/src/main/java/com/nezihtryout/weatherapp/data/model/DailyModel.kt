package com.nezihtryout.weatherapp.data.model

import com.nezihtryout.weatherapp.data.model.submodels.TempModel
import com.nezihtryout.weatherapp.data.model.submodels.WeatherDescModel

data class DailyModel(
    var dt: String? = null,
    val temp: TempModel? = null,
    val weather: ArrayList<WeatherDescModel>? = null
)
