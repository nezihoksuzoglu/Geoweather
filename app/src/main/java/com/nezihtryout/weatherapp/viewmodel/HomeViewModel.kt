package com.nezihtryout.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezihtryout.weatherapp.data.Result
import com.nezihtryout.weatherapp.data.domain.repository.HomeRepository
import com.nezihtryout.weatherapp.data.model.WeatherModel
import com.nezihtryout.weatherapp.data.model.submodels.CityModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    private val _locationTaskInfo: MutableLiveData<Result<WeatherModel>> = MutableLiveData()
    val locationTaskInfo: LiveData<Result<WeatherModel>> get() = _locationTaskInfo

    private val _resultInfo: MutableLiveData<Result<CityModel>> = MutableLiveData()
    val resultInfo: LiveData<Result<CityModel>> get() = _resultInfo
    private val TAG = "viewModel"

    fun readWeatherViewModel(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val APIResponse = repository.readWeatherFromAPI(lat, lon)) {
                is Result.Error -> Log.d(TAG, "Weather API Returns Error")
                is Result.Success -> _locationTaskInfo.postValue(APIResponse)
            }
            when (val APIResponse = repository.readCityNameFromAPI(lat, lon)) {
                is Result.Error -> Log.d(TAG, "City Name API Returns Error")
                is Result.Success -> _resultInfo.postValue(APIResponse)
            }
        }
    }
}
