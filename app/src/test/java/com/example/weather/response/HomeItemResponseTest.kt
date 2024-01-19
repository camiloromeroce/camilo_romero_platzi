package com.example.weather.response

import com.example.lib.model.response.HomeItemResponse
import org.junit.Test
import kotlin.test.assertEquals

class HomeItemResponseTest {

    // Sample data
    private val dt = 1642604400L
    private val temp = "25°C"
    private val icon = "sunny"

    @Test
    fun `HomeItemResponse properties should have correct values`() {
        // Given
        val homeItemResponse = HomeItemResponse(dt, temp, icon)

        // Then
        assertEquals(dt, homeItemResponse.dt)
        assertEquals(temp, homeItemResponse.temp)
        assertEquals(icon, homeItemResponse.icon)
    }
}
