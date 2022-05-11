package com.nezihtryout.weatherapp.application

import android.app.Application
import com.huawei.hms.maps.MapsInitializer
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApplication : Application()