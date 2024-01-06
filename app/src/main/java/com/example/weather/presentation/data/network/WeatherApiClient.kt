package com.example.weather.presentation.data.network

import com.example.lib.model.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiClient {

    @GET("weather")
    suspend fun getLocationsByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): Response <WeatherResponse>
}