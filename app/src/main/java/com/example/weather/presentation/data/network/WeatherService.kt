package com.example.weather.presentation.data.network

import com.example.lib.model.response.WeatherForecastResponse
import com.example.lib.model.response.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherService @Inject constructor(
    private val apiClient: WeatherApiClient
) {
    suspend fun getLocationsByCoordinates(
        latitude: Double,
        longitude: Double,
        apiKey: String = "62de9ae593781cbf28040d45dcf9be95"
    ): WeatherResponse = withContext(Dispatchers.IO) {
        val response =
            apiClient.getLocationsByCoordinates(latitude, longitude, apiKey)
        response.body() ?: WeatherResponse()
    }

    suspend fun getForecastWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String = "62de9ae593781cbf28040d45dcf9be95"
    ): WeatherForecastResponse = withContext(Dispatchers.IO) {
        val response =
            apiClient.getForecastWeather(latitude, longitude, apiKey)
        response.body() ?: WeatherForecastResponse()
    }

}