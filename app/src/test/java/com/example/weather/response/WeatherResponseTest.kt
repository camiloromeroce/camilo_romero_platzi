package com.example.weather.response

import com.example.lib.model.response.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class WeatherResponseTest {

    // Sample data
    private val weatherItem = WeatherResponse.Weather(
        id = 800,
        main = "Clear",
        description = "Clear sky",
        icon = "01d"
    )

    private val weatherResponse = WeatherResponse(
        coord = WeatherResponse.Coord(lon = 0.0, lat = 0.0),
        weather = listOf(weatherItem),
        base = "stations",
        main = WeatherResponse.Main(
            temp = 25.0,
            feelsLike = 23.5,
            tempMin = 22.0,
            tempMax = 27.0,
            pressure = 1015,
            humidity = 50,
            seaLevel = 1016,
            grndLevel = 1010
        ),
        visibility = 10000,
        wind = WeatherResponse.Wind(speed = 5.0, deg = 180, gust = 8.0),
        rain = WeatherResponse.Rain(rain1h = 0.5),
        clouds = WeatherResponse.Clouds(all = 20),
        dt = 1642604400L,
        sys = WeatherResponse.Sys(type = 1, id = 123, country = "US", sunrise = 1642590000L, sunset = 1642630000L),
        timezone = -28800,
        id = 123456,
        name = "City",
        cod = 200
    )

    @Test
    fun `WeatherResponse properties should have correct values`() {
        // Then
        assertNotNull(weatherResponse.coord)
        assertEquals(0.0, weatherResponse.coord?.lon)
        assertEquals(0.0, weatherResponse.coord?.lat)

        assertNotNull(weatherResponse.weather)
        assertEquals(1, weatherResponse.weather?.size)

        val weatherItem = weatherResponse.weather?.firstOrNull()
        assertEquals(800, weatherItem?.id)
        assertEquals("Clear", weatherItem?.main)
        assertEquals("Clear sky", weatherItem?.description)
        assertEquals("01d", weatherItem?.icon)

        assertEquals("stations", weatherResponse.base)

        assertNotNull(weatherResponse.main)
        assertEquals(25.0, weatherResponse.main?.temp)
        assertEquals(23.5, weatherResponse.main?.feelsLike)
        assertEquals(22.0, weatherResponse.main?.tempMin)
        assertEquals(27.0, weatherResponse.main?.tempMax)
        assertEquals(1015, weatherResponse.main?.pressure)
        assertEquals(50, weatherResponse.main?.humidity)
        assertEquals(1016, weatherResponse.main?.seaLevel)
        assertEquals(1010, weatherResponse.main?.grndLevel)

        assertEquals(10000, weatherResponse.visibility)

        assertNotNull(weatherResponse.wind)
        assertEquals(5.0, weatherResponse.wind?.speed)
        assertEquals(180, weatherResponse.wind?.deg)
        assertEquals(8.0, weatherResponse.wind?.gust)

        assertNotNull(weatherResponse.rain)
        assertEquals(0.5, weatherResponse.rain?.rain1h)

        assertNotNull(weatherResponse.clouds)
        assertEquals(20, weatherResponse.clouds?.all)

        assertEquals(1642604400L, weatherResponse.dt)

        assertNotNull(weatherResponse.sys)
        assertEquals(1, weatherResponse.sys?.type)
        assertEquals(123, weatherResponse.sys?.id)
        assertEquals("US", weatherResponse.sys?.country)
        assertEquals(1642590000L, weatherResponse.sys?.sunrise)
        assertEquals(1642630000L, weatherResponse.sys?.sunset)

        assertEquals(-28800, weatherResponse.timezone)

        assertEquals(123456, weatherResponse.id)
        assertEquals("City", weatherResponse.name)
        assertEquals(200, weatherResponse.cod)
    }
}
