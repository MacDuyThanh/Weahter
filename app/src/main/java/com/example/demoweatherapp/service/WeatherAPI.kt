package com.example.demoweatherapp.service

import com.example.demoweatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather?&units=metric&APPID=04a42b96398abc8e4183798ed22f9485")
    fun getData(@Query("q") cityName: String): Single<WeatherModel>
}