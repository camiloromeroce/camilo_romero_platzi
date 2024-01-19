package com.example.weather.response
import com.example.lib.model.response.ForecastItemResponse
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ForecastItemResponseTest {

    // Sample data
    private val highTemp = 30.0
    private val lowTemp = 20.0
    private val descriptionDay = 1642604400L
    private val weatherDescription = "Sunny"
    private val speed = 10.0
    private val nw = "Northwest"
    private val iconText = "Clear sky"
    private val icon = "clear_sky"

    @Test
    fun `ForecastItemResponse properties should have correct values`() {
        // Given
        val forecastItemResponse = ForecastItemResponse(
            highTemp, lowTemp, descriptionDay, weatherDescription,
            speed, nw, iconText, icon
        )

        // Then
        assertEquals(highTemp, forecastItemResponse.highTemp)
        assertEquals(lowTemp, forecastItemResponse.lowTemp)
        assertEquals(descriptionDay, forecastItemResponse.descriptionDay)
        assertEquals(weatherDescription, forecastItemResponse.weatherDescription)
        assertEquals(speed, forecastItemResponse.speed)
        assertEquals(nw, forecastItemResponse.nw)
        assertEquals(iconText, forecastItemResponse.iconText)
        assertEquals(icon, forecastItemResponse.icon)
    }

    @Test
    fun `ForecastItemResponse weatherDescription can be null`() {
        // Given
        val forecastItemResponse = ForecastItemResponse(
            highTemp, lowTemp, descriptionDay, null,
            speed, nw, iconText, icon
        )

        // Then
        assertNull(forecastItemResponse.weatherDescription)
    }
}
