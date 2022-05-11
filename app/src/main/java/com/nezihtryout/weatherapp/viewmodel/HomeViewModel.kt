package com.nezihtryout.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezihtryout.weatherapp.data.CityData
import com.nezihtryout.weatherapp.data.domain.repository.HomeRepository
import com.nezihtryout.weatherapp.data.WeatherData
import com.nezihtryout.weatherapp.data.model.WeatherModel
import com.nezihtryout.weatherapp.data.model.submodels.CityModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): ViewModel(){
    private val _locationTaskInfo: MutableLiveData<WeatherData<WeatherModel>> = MutableLiveData()
    val locationTaskInfo: LiveData<WeatherData<WeatherModel>> get() = _locationTaskInfo

    private val _cityDataInfo: MutableLiveData<CityData<CityModel>> = MutableLiveData()
    val cityDataInfo: LiveData<CityData<CityModel>> get() = _cityDataInfo

    private val TAG = "viewModel"

    fun readWeatherViewModel(lat : Double,lon : Double){
        viewModelScope.launch(Dispatchers.IO) {
            when (val APIResponse = repository.readWeatherFromAPI(lat,lon)){
                is WeatherData.Error -> Log.d(TAG+"_error","ViewModel gets WeatherData.Error")
                is WeatherData.Success -> _locationTaskInfo.postValue(APIResponse)
            }
            when (val APIResponse = repository.readCityNameFromAPI(lat,lon)){
                is CityData.Error -> Log.d(TAG+"_error","ViewModel gets CityData.Error")
                is CityData.Success ->  _cityDataInfo.postValue(APIResponse)
            }
        }
    }
}
