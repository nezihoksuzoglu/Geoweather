package com.nezihtryout.weatherapp.ui

import com.nezihtryout.weatherapp.data.model.WeatherModel
import com.nezihtryout.weatherapp.data.model.submodels.CityModel

data class HomeViewState(
    val cityModel: CityModel? = null,
    val weatherModel: WeatherModel? = null,
    val loading: Boolean = false,
    val error: Boolean = false
)
