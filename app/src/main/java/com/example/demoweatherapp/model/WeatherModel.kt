package com.example.demoweatherapp.model

data class WeatherModel(
    val id: Int,
    val name: String,
    val code: Int,
    val weather: List<Weather>,
    val main: Main,
    val coord: Coord,
    val wind: Wind

)