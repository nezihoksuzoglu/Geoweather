package com.nezihtryout.weatherapp.data.domain.repository

import android.util.Log
import com.nezihtryout.weatherapp.data.CityData
import com.nezihtryout.weatherapp.data.WeatherData
import com.nezihtryout.weatherapp.data.domain.WeatherServices
import com.nezihtryout.weatherapp.data.model.WeatherModel
import com.nezihtryout.weatherapp.data.model.submodels.CityModel
import com.nezihtryout.weatherapp.util.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt


class HomeRepository @Inject constructor(
    private val api: WeatherServices
) {
    private val TAG : String = "repo"
    suspend fun readWeatherFromAPI(lat : Double, lon : Double) : WeatherData<WeatherModel> {
        return try {
            val response = api.getWeatherData(lat, lon, oneCallAPIFormat , apiKey, weatherDataUnit)
            val result = response.body()
            if (response.isSuccessful && result != null) WeatherData.Success(editData(result))
            else WeatherData.Error(response.message())
        }
        catch(e: Exception) {
            WeatherData.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun readCityNameFromAPI(lat : Double, lon : Double) : CityData<CityModel>{
        return try {
            val response = api.getCurrentWeather(lat,lon, apiKey)
            val result = response.body()
            if (response.isSuccessful && result != null)  CityData.Success(result)
            else CityData.Error(response.message())
        }
        catch(e: Exception) {
            CityData.Error(e.message ?: "An error occurred")
        }
    }

    private fun editData(dataModel : WeatherModel): WeatherModel {

        // Editing CurrentModel
        if (dataModel.current != null) {
            if (dataModel.current.temp != null){
                dataModel.current.temp = editTempData(dataModel.current.temp!!) + "°"
            }
        }
        // Editing HourlyModel
        // For Every Hour
        // There is 23 Hours of data that is why scope is between 0..23
        for(i in 0..23){
            if (dataModel.hourly != null) {
                val hourlyModel = dataModel.hourly[i]
                // Editing to hour data from unix format
                if (hourlyModel.dt != null && hourlyModel.temp != null){
                    // Hour data formula
                    hourlyModel.dt = hourlyModel.dt!!.plus(dataModel.timezone_offset!!).rem(86400).div(3600)
                    hourlyModel.temp = editTempData(hourlyModel.temp!!) + "°"
                }
                dataModel.hourly[i] = hourlyModel
            }
        }

        // Editing DailyModel
        // For Every Day
        // i = 0 is today so its excluded
        if (dataModel.daily != null){
            for (i in 1 until dataModel.daily.size){
                val dailyModel = dataModel.daily[i]
                if (dailyModel.dt != null && dailyModel.temp != null){
                    dailyModel.dt = editDailyTimeData(dailyModel.dt!!)
                }
                if (dailyModel.temp != null){
                    if (dailyModel.temp.day != null && dailyModel.temp.night != null){
                        dailyModel.temp.day = editTempData(dailyModel.temp.day!!)
                        dailyModel.temp.night = editTempData(dailyModel.temp.night!!)
                    }
                }
                dataModel.daily[i] = dailyModel
            }
        }
        return dataModel
    }

    private fun editDailyTimeData(data : String) : String {
        return try {
            val sdf = SimpleDateFormat("EEEE") // TODO: Should've change that?
            val dateFormat = Date(data.toLong().times(1000))
            sdf.format(dateFormat)
        } catch (e : Exception){
            Log.d(TAG,e.message ?: "An error occurred at editDailyTimeData")
            "Default Day"
        }
    }

    private fun editTempData(data : String) : String{
        return data.toDouble().roundToInt().toString()
    }

}