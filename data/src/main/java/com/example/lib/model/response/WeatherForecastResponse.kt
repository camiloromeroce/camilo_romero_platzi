package com.example.lib.model.response

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class WeatherForecastResponse(
    @SerializedName("cod") val cod: String? = "",
    @SerializedName("message") val message: Int = 0,
    @SerializedName("cnt") val cnt: Int = 0,
    @SerializedName("list") val list: List<ForecastItem> = emptyList(),
    @SerializedName("city") val city: City = City()
) {
    data class ForecastItem(
        @SerializedName("dt") val dt: Long = 0,
        @SerializedName("main") val main: Main = Main(),
        @SerializedName("weather") val weather: List<Weather> = emptyList(),
        @SerializedName("clouds") val clouds: Clouds = Clouds(),
        @SerializedName("wind") val wind: Wind = Wind(),
        @SerializedName("visibility") val visibility: Int = 0,
        @SerializedName("pop") val pop: Double = 0.0,
        @SerializedName("rain") val rain: Rain? = null,
        @SerializedName("sys") val sys: Sys = Sys(),
        @SerializedName("dt_txt") val dtTxt: String = ""
    )

    data class Main(
        @SerializedName("temp") val temp: Double = 0.0,
        @SerializedName("feels_like") val feelsLike: Double = 0.0,
        @SerializedName("temp_min") val tempMin: Double = 0.0,
        @SerializedName("temp_max") val tempMax: Double = 0.0,
        @SerializedName("pressure") val pressure: Int = 0,
        @SerializedName("sea_level") val seaLevel: Int = 0,
        @SerializedName("grnd_level") val grndLevel: Int = 0,
        @SerializedName("humidity") val humidity: Int = 0,
        @SerializedName("temp_kf") val tempKf: Double = 0.0
    )

    data class Weather(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("main") val main: String = "",
        @SerializedName("description") val description: String = "",
        @SerializedName("icon") val icon: String = ""
    )

    data class Clouds(
        @SerializedName("all") val all: Int = 0
    )

    data class Wind(
        @SerializedName("speed") val speed: Double = 0.0,
        @SerializedName("deg") val deg: Int = 0,
        @SerializedName("gust") val gust: Double = 0.0
    )

    data class Rain(
        @SerializedName("3h") val `3h`: Double = 0.0
    )

    data class Sys(
        @SerializedName("pod") val pod: String = ""
    )

    data class City(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("name") val name: String = "",
        @SerializedName("coord") val coord: Coord = Coord(),
        @SerializedName("country") val country: String = "",
        @SerializedName("population") val population: Int = 0,
        @SerializedName("timezone") val timezone: Int = 0,
        @SerializedName("sunrise") val sunrise: Long = 0,
        @SerializedName("sunset") val sunset: Long = 0
    )

    data class Coord(
        @SerializedName("lat") val lat: Double = 0.0,
        @SerializedName("lon") val lon: Double = 0.0
    )

    fun toForecastFiveItemResponse(): List<HomeItemResponse> {
        return this.list.map {
            HomeItemResponse(
                dt = it.dt,
                temp = it.main.temp.toString(),
                icon = it.weather.firstOrNull()?.icon.orEmpty()
            )
        }
    }

    fun toForecastAllItemResponse(): List<ForecastItemResponse> {
        val groupedByDay = this.list.groupBy {
            val date = Date(it.dt * 1000)
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
        }

        return groupedByDay.flatMap { (_, itemsForDay) ->
            val iconTextCounts = itemsForDay
                .map { it.weather.firstOrNull()?.main }
                .groupingBy { it }
                .eachCount()

            val mostRepeatedIconText = iconTextCounts.maxByOrNull { it.value }?.key

            val iconCounts = itemsForDay
                .map { it.weather.firstOrNull()?.icon }
                .groupingBy { it }
                .eachCount()

            val mostRepeatedIcon = iconCounts.maxByOrNull { it.value }?.key


            itemsForDay.map {
                ForecastItemResponse(
                    highTemp = it.main.tempMax,
                    lowTemp = it.main.tempMin,
                    descriptionDay = it.dt,
                    weatherDescription = it.weather.firstOrNull()?.description,
                    speed = it.wind.speed,
                    nw = "",
                    iconText = mostRepeatedIconText,
                    icon = mostRepeatedIcon,
                )
            }
        }
    }
}