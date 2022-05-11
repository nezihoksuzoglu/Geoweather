package com.nezihtryout.weatherapp.data.model

import com.nezihtryout.weatherapp.data.model.submodels.WeatherDescModel

data class HourlyModel(
    var dt : Long? = null,
    var temp : String? = null,
    //val feels_like : Double? = null,
    //val pressure : Int? = null,
    //val humidity : Int? = null,
    //val dew_point : Double? = null,
    //val uvi : Double? = null,
    //val clouds : Int? = null,
    //val visibility : Int? = null,
    //val wind_speed : Double? = null,
    //val wind_deg : Int? = null,
    //val wind_gust : Double? = null,
    val weather : Array<WeatherDescModel>? = null,
    //val pop : Double? = null
)
