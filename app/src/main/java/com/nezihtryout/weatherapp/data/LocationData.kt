package com.nezihtryout.weatherapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object LocationData {
    private val _locationDimenInfo: MutableLiveData<Array<Double>> = MutableLiveData()
    val locationDimenInfo: LiveData<Array<Double>> get() = _locationDimenInfo

    fun setDimensions(dim : Array<Double>){
        _locationDimenInfo.postValue(dim)
    }
}