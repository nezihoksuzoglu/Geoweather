package com.nezihtryout.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("lat") val lat : Double? = null,
    @SerializedName("lon") val lon : Double? = null,
    @SerializedName("timezone") val timezone : String? = null,
    @SerializedName("timezone_offset") val timezone_offset : Int? = null,
    @SerializedName("current") val current : CurrentModel? = null,
    @SerializedName("hourly") val hourly : HourlyModel? = null
)
