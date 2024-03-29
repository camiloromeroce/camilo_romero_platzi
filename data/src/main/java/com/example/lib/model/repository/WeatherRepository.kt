package com.example.lib.model.repository

import com.example.lib.model.response.WeatherForecastResponse
import com.example.lib.model.response.WeatherResponse
import com.example.lib.model.source.RemoteDataSource

class WeatherRepository(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getLocationsByCoordinates(
        latitude: Double,
        longitude: Double
    ): WeatherResponse =
        remoteDataSource.getCoordinatesByLocations(latitude, longitude)

    suspend fun getForecastWeather(
        latitude: Double,
        longitude: Double
    ): WeatherForecastResponse =
        remoteDataSource.getForecastWeather(latitude, longitude)
}