package com.nezihtryout.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class HourlyModel(
    @SerializedName("dt") val dt : Float? = null,
    @SerializedName("temp") val temp : Double? = null,
    @SerializedName("feels_like") val feels_like : Double? = null,
    @SerializedName("pressure") val pressure : Int? = null,
    @SerializedName("humidity") val humidity : Int? = null,
    @SerializedName("dew_point") val dew_point : Double? = null,
    @SerializedName("uvi") val uvi : Int? = null,
    @SerializedName("clouds") val clouds : Int? = null,
    @SerializedName("visibility") val visibility : Int? = null,
    @SerializedName("wind_speed") val wind_speed : Double? = null,
    @SerializedName("wind_deg") val wind_deg : Int? = null,
    @SerializedName("wind_gust") val wind_gust : Double? = null,
    @SerializedName("weather") val weather : WeatherModel? = null,
    @SerializedName("pop") val pop : Int? = null
)
