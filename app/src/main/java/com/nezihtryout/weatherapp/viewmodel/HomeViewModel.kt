package com.nezihtryout.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezihtryout.weatherapp.data.domain.repository.HomeRepository
import com.nezihtryout.weatherapp.data.Resource
import com.nezihtryout.weatherapp.data.model.WeatherModel
import com.nezihtryout.weatherapp.ui.fragment.HomeFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): ViewModel(){
    private val _locationTaskInfo: MutableLiveData<Resource<WeatherModel>> = MutableLiveData()
    val locationTaskInfo: LiveData<Resource<WeatherModel>> get() = _locationTaskInfo
    private val TAG = "viewModel"

    fun readWeatherViewModel(lat : Double,lon : Double){
        viewModelScope.launch(Dispatchers.IO) {
            when (val APIResponse = repository.readWeatherFromAPI(lat,lon)){
                is Resource.Error -> Log.d(TAG+"_error","ViewModel gets Resource.Error")
                is Resource.Success -> _locationTaskInfo.postValue(APIResponse)
            }
        }
    }
}