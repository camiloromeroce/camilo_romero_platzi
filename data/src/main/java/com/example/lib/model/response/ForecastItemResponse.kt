package com.example.lib.model.response

data class ForecastItemResponse(
    val highTemp: Double,
    val lowTemp: Double,
    val descriptionDay: Long,
    val weatherDescription: String?,
    val speed: Double,
    val nw: String?,
    val iconText: String?,
    val icon: String?
)
