package com.example.weather.response

import com.example.lib.model.response.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class WeatherForecastResponseTest {

    // Sample data
    private val forecastItem = WeatherForecastResponse.ForecastItem(
        dt = 1642604400L,
        main = WeatherForecastResponse.Main(temp = 25.0),
        weather = listOf(WeatherForecastResponse.Weather(id = 800, main = "Clear", description = "Clear sky", icon = "01d"))
    )

    private val weatherForecastResponse = WeatherForecastResponse(
        cod = "200",
        message = 0,
        cnt = 5,
        list = listOf(forecastItem),
        city = WeatherForecastResponse.City(name = "City", coord = WeatherForecastResponse.Coord(lat = 0.0, lon = 0.0))
    )

    @Test
    fun `toForecastFiveItemResponse should return a list of HomeItemResponse`() {
        // When
        val result = weatherForecastResponse.toForecastFiveItemResponse()

        // Then
        assertNotNull(result)
        assertEquals(1, result.size)

        val homeItemResponse = result[0]
        assertEquals(forecastItem.dt, homeItemResponse.dt)
        assertEquals(forecastItem.main.temp.toString(), homeItemResponse.temp)
        assertEquals(forecastItem.weather.firstOrNull()?.icon.orEmpty(), homeItemResponse.icon)
    }

    @Test
    fun `toForecastAllItemResponse should return a list of ForecastItemResponse`() {
        // When
        val result = weatherForecastResponse.toForecastAllItemResponse()

        // Then
        assertNotNull(result)
        assertEquals(1, result.size)

        val forecastItemResponse = result[0]
        assertEquals(forecastItem.main.tempMax, forecastItemResponse.highTemp)
        assertEquals(forecastItem.main.tempMin, forecastItemResponse.lowTemp)
        assertEquals(forecastItem.dt, forecastItemResponse.descriptionDay)
        assertEquals(forecastItem.weather.firstOrNull()?.description, forecastItemResponse.weatherDescription)
        assertEquals(forecastItem.wind.speed, forecastItemResponse.speed)
        assertEquals("", forecastItemResponse.nw)
        assertEquals("", forecastItemResponse.iconText)
        assertEquals("", forecastItemResponse.icon)
    }
}
