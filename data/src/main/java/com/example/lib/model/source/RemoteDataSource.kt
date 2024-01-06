package com.example.lib.model.source

import com.example.lib.model.response.WeatherResponse

interface RemoteDataSource {
    suspend fun getCoordinatesByLocations(latitude: Double, longitude: Double): WeatherResponse
}