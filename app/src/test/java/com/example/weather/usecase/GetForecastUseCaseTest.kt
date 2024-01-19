package com.example.weather.usecase
import com.example.lib.model.repository.WeatherRepository
import com.example.lib.model.response.WeatherForecastResponse
import com.example.usecases.home.GetForecastUseCase
import com.example.usecases.home.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import java.io.IOException

@ExperimentalCoroutinesApi
class GetForecastUseCaseTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    // Mocks
    private val repository: WeatherRepository = mock()
    private lateinit var getForecastUseCase: GetForecastUseCase

    // Sample data
    private val latitude = 37.7749
    private val longitude = -122.4194

    @Before
    fun setUp() {
        getForecastUseCase = GetForecastUseCase(repository)
    }


}
