package com.example.weather.presentation.data.network.source

import com.example.lib.model.response.WeatherForecastResponse
import com.example.lib.model.response.WeatherResponse
import com.example.lib.model.source.RemoteDataSource
import com.example.weather.presentation.data.network.WeatherService

class RemoteDataSourceImp(
    private val service: WeatherService
) : RemoteDataSource {

    override suspend fun getCoordinatesByLocations(
        latitude: Double,
        longitude: Double
    ): WeatherResponse {
        return service.getLocationsByCoordinates(latitude, longitude)
    }

    override suspend fun getForecastWeather(
        latitude: Double,
        longitude: Double
    ): WeatherForecastResponse {
        return service.getForecastWeather(latitude, longitude)
    }
}