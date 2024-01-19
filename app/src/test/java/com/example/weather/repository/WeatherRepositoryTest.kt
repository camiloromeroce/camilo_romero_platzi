package com.example.weather.repository

import com.example.lib.model.repository.WeatherRepository
import com.example.lib.model.response.WeatherForecastResponse
import com.example.lib.model.response.WeatherResponse
import com.example.lib.model.source.RemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class WeatherRepositoryTest {

    // Mocks
    private val remoteDataSource: RemoteDataSource = mock()
    private val weatherRepository = WeatherRepository(remoteDataSource)

    // Sample data
    private val latitude = 37.7749
    private val longitude = -122.4194

    @Before
    fun setUp() {
        // Reset mocks before each test
        reset(remoteDataSource)
    }

    @Test
    fun `getLocationsByCoordinates should return WeatherResponse`() = runBlocking {
        // Given
        val mockWeatherResponse: WeatherResponse = mock()
        whenever(remoteDataSource.getCoordinatesByLocations(latitude, longitude))
            .thenReturn(mockWeatherResponse)

        // When
        val result: WeatherResponse = weatherRepository.getLocationsByCoordinates(latitude, longitude)

        // Then
        assert(result == mockWeatherResponse)
        verify(remoteDataSource).getCoordinatesByLocations(latitude, longitude)
    }

    @Test
    fun `getForecastWeather should return WeatherForecastResponse`() = runBlocking {
        // Given
        val mockWeatherForecastResponse: WeatherForecastResponse = mock()
        whenever(remoteDataSource.getForecastWeather(latitude, longitude))
            .thenReturn(mockWeatherForecastResponse)

        // When
        val result: WeatherForecastResponse = weatherRepository.getForecastWeather(latitude, longitude)

        // Then
        assert(result == mockWeatherForecastResponse)
        verify(remoteDataSource).getForecastWeather(latitude, longitude)
    }
}
