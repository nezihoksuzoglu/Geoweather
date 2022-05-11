package com.nezihtryout.weatherapp.util

import android.util.Log
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.location.*
import com.nezihtryout.weatherapp.data.LocationData

class LocationManagerClass {
    private val TAG = "locMng"

    fun getLocation(fusedLocationProviderClient : FusedLocationProviderClient){
        val lastLocation = fusedLocationProviderClient.lastLocation
        lastLocation.addOnSuccessListener(OnSuccessListener { location ->
            if (location == null) {
                Log.d(TAG+"_locNull","Location is null.")

                return@OnSuccessListener
            }
            Log.d(TAG+"_locOK","Location data secured.")
            LocationData.setDimensions(arrayOf(location.latitude,location.longitude))
            return@OnSuccessListener
        })
            // Define callback for failure in obtaining the last known location.
            .addOnFailureListener {
                Log.d(TAG+"_locOnFail","lastLocation.onFailure")
            }
    }

    fun checkLocationData(mLocationRequest : LocationRequest, settingsClient : SettingsClient){
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()
        // Check the device location settings.
        settingsClient.checkLocationSettings(locationSettingsRequest)
            // Define the listener for success in calling the API for checking device location settings.
            .addOnSuccessListener {
                Log.i(TAG + "_conOK", "checkLocationSetting onComplete")
            }
            // Define callback for failure in checking the device location settings.
            .addOnFailureListener { e ->
                Log.i(TAG + "_conFail", "checkLocationSetting onFailure:" + e.message)
            }
    }
}