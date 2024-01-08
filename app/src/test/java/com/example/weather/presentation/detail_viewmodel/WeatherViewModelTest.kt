
import com.example.lib.model.response.WeatherForecastResponse
import com.example.lib.model.response.WeatherResponse
import com.example.usecases.home.GetForecastUseCase
import com.example.usecases.home.GetLocationsByCoordinatesUseCase
import com.example.usecases.home.Resource
import com.example.weather.presentation.home.viewmodel.WeatherViewModel
import com.example.weather.presentation.state.WeatherState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @Mock
    lateinit var getLocationsByCoordinatesUseCase: GetLocationsByCoordinatesUseCase

    @Mock
    lateinit var getForecastUseCase: GetForecastUseCase

    @InjectMocks
    lateinit var viewModel: WeatherViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test init with success response`() = runBlockingTest {
        val location = WeatherResponse()
        val successResource = Resource.Success(location)

        `when`(getLocationsByCoordinatesUseCase(viewModel.latitude, viewModel.longitude))
            .thenReturn(flowOf(successResource))

        viewModel.init()

        assertEquals(viewModel.state.value, WeatherState.ShowWeatherData(location))
    }

    @Test
    fun `test init with error response`() = runBlockingTest {
        val errorMessage = "Error message"
        val errorResource: Resource<WeatherResponse> = Resource.Error(errorMessage)

        `when`(getLocationsByCoordinatesUseCase(viewModel.latitude, viewModel.longitude))
            .thenReturn(flowOf(errorResource))

        viewModel.init()

        assertEquals(viewModel.state.value, WeatherState.Error(errorMessage))
    }

    @Test
    fun `test getForecast with success response`() = runBlockingTest {
        val forecast = WeatherForecastResponse()
        val successResource = Resource.Success(forecast)

        `when`(getForecastUseCase(viewModel.latitude, viewModel.longitude))
            .thenReturn(flowOf(successResource))

        viewModel.getForecast()

        assertEquals(viewModel.state.value, WeatherState.ShowForecastData(forecast))
    }

    @Test
    fun `test getForecast with error response`() = runBlockingTest {
        val errorMessage = "Error message"
        val errorResource: Resource<WeatherForecastResponse> = Resource.Error(errorMessage)

        `when`(getForecastUseCase(viewModel.latitude, viewModel.longitude))
            .thenReturn(flowOf(errorResource))

        viewModel.getForecast()

        assertEquals(viewModel.state.value, WeatherState.Error(errorMessage))
    }
}
