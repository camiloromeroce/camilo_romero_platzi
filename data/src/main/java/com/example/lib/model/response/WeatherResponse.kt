package com.example.lib.model.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord") val coord: Coord? = Coord(),
    @SerializedName("weather") val weather: List<Weather>? = emptyList(),
    @SerializedName("base") val base: String? = "",
    @SerializedName("main") val main: Main? = Main(),
    @SerializedName("visibility") val visibility: Int? = 0,
    @SerializedName("wind") val wind: Wind? = Wind(),
    @SerializedName("rain") val rain: Rain? = Rain(),
    @SerializedName("clouds") val clouds: Clouds? = Clouds(),
    @SerializedName("dt") val dt: Long? = 0,
    @SerializedName("sys") val sys: Sys? = Sys(),
    @SerializedName("timezone") val timezone: Int? = 0,
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("cod") val cod: Int? = 0
) {
    data class Coord(
        @SerializedName("lon") val lon: Double? = 0.0,
        @SerializedName("lat") val lat: Double? = 0.0
    )

    data class Weather(
        @SerializedName("id") val id: Int?,
        @SerializedName("main") val main: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("icon") val icon: String?
    ){
        fun toWeatherItem(): WeatherItem {
            return WeatherItem(
                id = this.id,
                main = this.main,
                description = this.description,
                icon = this.icon
            )
        }
    }

    data class Main(
        @SerializedName("temp") val temp: Double? = 0.0,
        @SerializedName("feels_like") val feelsLike: Double? = 0.0,
        @SerializedName("temp_min") val tempMin: Double? = 0.0,
        @SerializedName("temp_max") val tempMax: Double? = 0.0,
        @SerializedName("pressure") val pressure: Int? = 0,
        @SerializedName("humidity") val humidity: Int? = 0,
        @SerializedName("sea_level") val seaLevel: Int? = 0,
        @SerializedName("grnd_level") val grndLevel: Int? = 0
    )

    data class Wind(
        @SerializedName("speed") val speed: Double? = 0.0,
        @SerializedName("deg") val deg: Int? = 0,
        @SerializedName("gust") val gust: Double? = 0.0
    )

    data class Rain(
        @SerializedName("1h") val rain1h: Double? = 0.0
    )

    data class Clouds(
        @SerializedName("all") val all: Int? = 0
    )

    data class Sys(
        @SerializedName("type") val type: Int? = 0,
        @SerializedName("id") val id: Int? = 0,
        @SerializedName("country") val country: String? = "",
        @SerializedName("sunrise") val sunrise: Long? = 0,
        @SerializedName("sunset") val sunset: Long? = 0
    )
}