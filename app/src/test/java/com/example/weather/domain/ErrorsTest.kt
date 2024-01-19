package com.example.weather.domain

import com.example.domain.common.Errors
import org.junit.Test
import kotlin.test.assertEquals

class ErrorsTest {

    @Test
    fun `error codes should match expectations`() {
        assertEquals(331, Errors.notLocationsErrorCode)
        assertEquals(332, Errors.notWeatherStateErrorCode)
        assertEquals(333, Errors.notFiltersErrorCode)
        assertEquals(334, Errors.connectionErrorCode)
        assertEquals(335, Errors.unknownErrorCode)

    }
}
