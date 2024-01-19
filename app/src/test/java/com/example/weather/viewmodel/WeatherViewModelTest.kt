package com.example.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lib.model.response.WeatherForecastResponse
import com.example.usecases.home.GetForecastUseCase
import com.example.usecases.home.GetLocationsByCoordinatesUseCase
import com.example.usecases.home.Resource
import com.example.weather.CoroutinesTestRule
import com.example.weather.presentation.home.viewmodel.WeatherViewModel
import com.example.weather.presentation.state.WeatherState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    // Mocks
    private val getForecastUseCase: GetForecastUseCase = mock()

    private val getLocationsByCoordinatesUseCase: GetLocationsByCoordinatesUseCase = mock()

    // Class under test
    private lateinit var weatherViewModel: WeatherViewModel

    // Sample data
    private val latitude = 37.7749
    private val longitude = -122.4194


    @Before
    fun setUp() {
        weatherViewModel = WeatherViewModel(getLocationsByCoordinatesUseCase, getForecastUseCase)
    }

    @Test
    fun `getForecast should set LoadingState and ShowForecastData on success`() =

        testCoroutineScope.runBlockingTest {
            // Given
            val mockForecast: WeatherForecastResponse = mock()
            whenever(getForecastUseCase(latitude, longitude)).thenReturn(flow {
                emit(Resource.Loading())
                emit(Resource.Success(mockForecast))
            })

            // When
            weatherViewModel.getForecast()

            // Then
            verify(getForecastUseCase).invoke(latitude, longitude)
            assertEquals(WeatherState.LoadingState, weatherViewModel.state.value)

            // Advance the dispatcher to execute the coroutine
            testDispatcher.advanceUntilIdle()

            assertEquals(WeatherState.ShowForecastData(mockForecast), weatherViewModel.state.value)
        }

    @Test
    fun `getForecast should set LoadingState and Error on error`() =

        testCoroutineScope.runBlockingTest { // Use coroutinesTestRule here
            // Given
            val errorMessage = "Some error message"
            whenever(getForecastUseCase(latitude, longitude)).thenReturn(flow {
                emit(Resource.Loading())
                emit(Resource.Error(errorMessage))
            })

            // When
            weatherViewModel.getForecast()

            // Then
            verify(getForecastUseCase).invoke(latitude, longitude)
            assertEquals(WeatherState.LoadingState, weatherViewModel.state.value)

            // Advance the dispatcher to execute the coroutine
            testDispatcher.advanceUntilIdle()

            assertEquals(WeatherState.Error(errorMessage), weatherViewModel.state.value)
        }


    // Add more test cases as needed
}

