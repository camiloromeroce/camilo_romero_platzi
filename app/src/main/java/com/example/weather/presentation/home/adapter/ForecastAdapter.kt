package com.example.weather.presentation.home.adapter

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lib.model.response.ForecastItemResponse
import com.example.weather.R
import com.example.weather.databinding.ItemViewWeatherLandingBinding
import com.example.weather.presentation.inflate
import com.example.weather.presentation.loadUrl
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ForecastAdapter(private val listener: (ForecastItemResponse) -> Unit) :
    ListAdapter<ForecastItemResponse, ForecastAdapter.ViewHolder>(DiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_view_weather_landing, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecastItemResponse = getItem(position)
        holder.bind(forecastItemResponse)
        holder.itemView.apply { setOnClickListener { listener(forecastItemResponse) } }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemViewWeatherLandingBinding =
            ItemViewWeatherLandingBinding.bind(view)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: ForecastItemResponse) = with(binding) {
            hiTempText?.text = buildString {
                append("Hi Temp: ")
                append(item.highTemp)
                append("°F")
            }

            lowTempText?.text = buildString {
                append("Low Temp: ")
                append(item.highTemp)
                append("°F")
            }
            val dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(item.descriptionDay),
                ZoneId.systemDefault()
            )

            val formattedTime = dateTime.format(DateTimeFormatter.ofPattern("EEEE, MMMM d"))
            dayText?.text = formattedTime
            windSpeed?.text = item.weatherDescription
            windSpeedValue?.text = buildString {
                append(item.speed.toString())
                append(" mph")
            }
            nwValue?.text = item.nw
            iconForecastDescription?.text = item.iconText
            iconForecastItem?.loadUrl("https://openweathermap.org/img/wn/${item.icon}.png")
        }
    }
}

private object DiffUtil : DiffUtil.ItemCallback<ForecastItemResponse>() {
    override fun areItemsTheSame(
        oldItem: ForecastItemResponse, newItem: ForecastItemResponse,
    ): Boolean = oldItem.highTemp == newItem.highTemp

    override fun areContentsTheSame(
        oldItem: ForecastItemResponse, newItem: ForecastItemResponse,
    ): Boolean = oldItem == newItem
}