package com.nezihtryout.weatherapp.data.model

data class WeatherModel(
    val lat : Double? = null,
    val lon : Double? = null,
    val timezone : String? = null,
    val timezone_offset : Int? = null,
    val current : CurrentModel? = null,
    val hourly : Array<HourlyModel>? = null,
    val daily : Array<DailyModel>? = null
)
