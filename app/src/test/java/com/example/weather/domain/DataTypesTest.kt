package com.example.weather.domain

import com.example.domain.common.DataTypes
import org.junit.Test
import kotlin.test.assertEquals

class DataTypesTest {

    @Test
    fun `enum values should match expectations`() {
        assertEquals("TEMPERATURE", DataTypes.TEMPERATURE.name)
        assertEquals("PERCENT", DataTypes.PERCENT.name)
        assertEquals("SPEED", DataTypes.SPEED.name)
        assertEquals("DATE", DataTypes.DATE.name)
    }
}
