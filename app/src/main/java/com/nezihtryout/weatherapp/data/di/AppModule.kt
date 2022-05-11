package com.nezihtryout.weatherapp.data.di

import android.content.Context
import android.util.Log
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.location.FusedLocationProviderClient
import com.huawei.hms.location.LocationRequest
import com.huawei.hms.location.LocationServices
import com.nezihtryout.weatherapp.application.BaseApplication
import com.nezihtryout.weatherapp.data.LocationData
import com.nezihtryout.weatherapp.data.domain.WeatherServices
import com.nezihtryout.weatherapp.util.baseURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication{
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideWeatherApi() : WeatherServices = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherServices::class.java)

    @Singleton
    @Provides
    fun provideLocationRequest() : LocationRequest{
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.numUpdates = 1
        return mLocationRequest
    }
}