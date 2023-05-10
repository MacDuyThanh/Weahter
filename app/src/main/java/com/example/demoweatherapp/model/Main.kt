package com.example.demoweatherapp.model

data class Main(
    val temp: Double,
    val feals_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int,
    val grnd_level: Int
)
