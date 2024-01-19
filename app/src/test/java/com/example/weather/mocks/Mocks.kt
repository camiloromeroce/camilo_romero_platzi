package com.example.weather.mocks


const val sectionMock = "sectionMock"

object Mocks {
    val mockLocationsData = LocationsData(
        name = "Mock City",
        latitude = 4.6927,
        longitude = 74.0939,
        cod = "200"
    )
}


data class LocationsData(val name: String, val latitude: Double, val longitude: Double, val cod :String)