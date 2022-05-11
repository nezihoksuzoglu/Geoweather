package com.nezihtryout.weatherapp.data.model

import com.nezihtryout.weatherapp.data.model.submodels.TempModel
import com.nezihtryout.weatherapp.data.model.submodels.WeatherDescModel

data class DailyModel(
    var dt : String? = null,
    //val sunrise : Float? = null,
    //val sunset : Float? = null,
    //val moonrise : Float? = null,
    //val moonset : Float? = null,
    //val moon_phase : Double? = null,
    val temp : TempModel? = null,
    //val feels_like : FeelsLikeModel? = null,
    //val pressure : Int? = null,
    //val humidity : Int? = null,
    //val dew_point : Double? = null,
    //val wind_speed : Double? = null,
    //val wind_deg : Int? = null,
    //val wind_gust : Double? = null,
    val weather : Array<WeatherDescModel>? = null,
    //val clouds : Int? = null,
    //val pop : Double? = null,
    //val uvi : Double? = null
)
