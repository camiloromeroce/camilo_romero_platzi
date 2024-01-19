package com.example.weather.presentation.detail_viewmodel

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.common.Errors.Companion.notLocationsErrorCode
import com.example.lib.model.response.WeatherForecastResponse
import com.example.lib.model.response.WeatherResponse
import com.example.usecases.home.GetForecastUseCase
import com.example.usecases.home.GetLocationsByCoordinatesUseCase
import com.example.usecases.home.Resource
import com.example.weather.mocks.Mocks
import com.example.weather.presentation.home.viewmodel.WeatherViewModel
import com.example.weather.presentation.state.WeatherState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.fail

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    // Rule to swap the background executor used by the Architecture Components with a different one that executes each task synchronously.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Rule to set the main dispatcher to a TestCoroutineDispatcher
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Mock the GetLocationsByCoordinatesUseCase
    @Mock
    lateinit var getLocationsByCoordinatesUseCase: GetLocationsByCoordinatesUseCase

    //Mock the GetForecastUseCase if needed
    @Mock
    lateinit var getForecastUseCase: GetForecastUseCase

    private lateinit var weatherViewModel: WeatherViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        weatherViewModel = WeatherViewModel(getLocationsByCoordinatesUseCase, getForecastUseCase)
    }

    @After
    fun tearDown() {
        // Do any cleanup here
    }

    @Test
    fun `test getForecast success`() = runBlocking {
        // Given
        val mockLocation = Mocks.mockLocationsData
        val successResource = Resource.Success(mockLocation)

        `when`(getForecastUseCase(mockLocation.latitude, mockLocation.longitude)).thenReturn(
            flowOf(
                successResource as Resource<WeatherForecastResponse>
            )
        )

        // When
        weatherViewModel.getForecast()

        // Then
        val currentState = weatherViewModel.state.value
        if (currentState is WeatherState.ShowForecastData) {
            assertEquals("200", currentState.forecastResponse.cod)
        } else {
            fail("Unexpected state type")
        }
    }

    @Test
    fun `test getForecast with empty cod`() = runBlocking {
        // Given
        val emptyCodForecast = Mocks.mockLocationsData.copy(cod = "")
        val mockLocation = Mocks.mockLocationsData
        val successResource = Resource.Success(mockLocation)

        `when`(getForecastUseCase(mockLocation.latitude, mockLocation.longitude)).thenReturn(
            flowOf(
                successResource as Resource<WeatherForecastResponse>
            )
        )
        // When
        weatherViewModel.getForecast()

        // Then
        val currentState = weatherViewModel.state.value
        if (currentState is WeatherState.Error) {
            assertEquals(notLocationsErrorCode.toString(), currentState.errorMessage)
        } else {
            fail("Unexpected state type")
        }
    }

    @Test
    fun `test refreshing data success`() = runBlocking {
        // Given
        val mockLocation = Mocks.mockLocationsData
        val successResource = Resource.Success(mockLocation)
        val expectedLatitude = Mocks.mockLocationsData.latitude
        val expectedLongitude = Mocks.mockLocationsData.longitude

        // Assuming LocationsData is part of WeatherResponse
        `when`(getLocationsByCoordinatesUseCase(mockLocation.latitude, 74.0939)).thenReturn(
            flowOf(
                successResource as Resource<WeatherResponse>
            )
        )

        // When
        weatherViewModel.refreshData()

        // Then
        val currentState = weatherViewModel.state.value
        if (currentState is WeatherState.ShowWeatherData) {
            val latitude = currentState.weatherResponse.coord?.lat
            val longitude = currentState.weatherResponse.coord?.lon

            // Now you can use the latitude and longitude in your assertions
            assertEquals(expectedLatitude, latitude)
            assertEquals(expectedLongitude, longitude)
        } else {
            fail("Unexpected state type")
        }
    }
}
