package com.nezihtryout.weatherapp.data.model

data class WeatherModel(
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
    val timezone_offset: Int? = null,
    val current: CurrentModel? = null,
    val hourly: ArrayList<HourlyModel>? = null,
    val daily: ArrayList<DailyModel>? = null
)
