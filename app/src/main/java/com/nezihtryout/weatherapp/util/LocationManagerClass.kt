package com.nezihtryout.weatherapp.util

import android.util.Log
import com.huawei.hms.location.LocationRequest
import com.huawei.hms.location.LocationSettingsRequest
import com.huawei.hms.location.SettingsClient

class LocationManagerClass {
    private val TAG = "LocationManager"

    fun checkLocationData(mLocationRequest: LocationRequest, settingsClient: SettingsClient) {
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