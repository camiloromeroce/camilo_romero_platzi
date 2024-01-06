package com.example.weather.presentation.state

sealed class WeatherState {
    object LoadingState : WeatherState()
    data class Error(val errorMessage: String) : WeatherState()

}