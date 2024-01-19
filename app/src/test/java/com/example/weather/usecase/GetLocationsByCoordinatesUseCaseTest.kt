package com.example.weather.usecase

import com.example.lib.model.repository.WeatherRepository
import com.example.usecases.home.GetLocationsByCoordinatesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class GetLocationsByCoordinatesUseCaseTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    // Mocks
    private val repository: WeatherRepository = mock()
    private lateinit var getLocationsByCoordinatesUseCase: GetLocationsByCoordinatesUseCase

    // Sample data
    private val latitude = 37.7749
    private val longitude = -122.4194

    @Before
    fun setUp() {
        getLocationsByCoordinatesUseCase = GetLocationsByCoordinatesUseCase(repository)
    }
}
