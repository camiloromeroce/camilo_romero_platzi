package com.example.lib.model.response

data class ForecastItemResponse(
    val hTemp: Double,
    val lowTemp: Double,
    val descriptionDay: String?,
    val weatherDescription: String?,
    val speed: Double,
    val nw: String?,
    val iconText: String?,
    val icon: String?
)
