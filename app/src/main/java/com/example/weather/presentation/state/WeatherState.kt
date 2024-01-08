package com.example.weather.presentation.state

import com.example.lib.model.response.WeatherForecastResponse
import com.example.lib.model.response.WeatherResponse

sealed class WeatherState {
    object LoadingState : WeatherState()
    data class Error(val errorMessage: String) : WeatherState()

    data class ShowWeatherData(val weatherResponse: WeatherResponse): WeatherState()
    data class ShowForecastData(val forecastResponse: WeatherForecastResponse): WeatherState()
}