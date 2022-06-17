package com.nezihtryout.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezihtryout.weatherapp.data.Result
import com.nezihtryout.weatherapp.data.domain.repository.HomeRepository
import com.nezihtryout.weatherapp.data.model.WeatherModel
import com.nezihtryout.weatherapp.ui.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    private val _locationTaskInfo: MutableLiveData<Result<WeatherModel>?> = MutableLiveData()
    val locationTaskInfo: LiveData<Result<WeatherModel>?> get() = _locationTaskInfo

    private val _cityState: MutableLiveData<HomeViewState> = MutableLiveData()
    val cityState: LiveData<HomeViewState> get() = _cityState
    private val _weatherState: MutableLiveData<HomeViewState> = MutableLiveData()
    val weatherState: LiveData<HomeViewState> get() = _weatherState

    fun readWeatherViewModel(lat: Double, lon: Double) {
        viewModelScope.launch {
            when (val APIResponse = repository.readWeatherFromAPI(lat, lon)) {
                is Result.Error -> _weatherState.postValue(HomeViewState(error = true))
                is Result.Success -> _weatherState.postValue(HomeViewState(weatherModel = APIResponse.data))
            }
            when (val APIResponse = repository.readCityNameFromAPI(lat, lon)) {
                is Result.Error -> _cityState.postValue(HomeViewState(error = true))
                is Result.Success -> _cityState.postValue(HomeViewState(cityModel = APIResponse.data))
            }
        }
    }
}
