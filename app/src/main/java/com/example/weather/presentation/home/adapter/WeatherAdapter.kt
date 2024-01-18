package com.example.weather.presentation.home.adapter

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lib.model.response.ForecastItemFiveResponse
import com.example.weather.R
import com.example.weather.databinding.ItemViewWeatherLandingBinding
import com.example.weather.presentation.inflate
import com.example.weather.presentation.loadUrl
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class WeatherAdapter(private val listener: (ForecastItemFiveResponse) -> Unit) :
    ListAdapter<ForecastItemFiveResponse, WeatherAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_view_weather_landing, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherItem = getItem(position)
        holder.bind(weatherItem)
        holder.itemView.apply { setOnClickListener { listener(weatherItem) } }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemViewWeatherLandingBinding =
            ItemViewWeatherLandingBinding.bind(view)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: ForecastItemFiveResponse) = with(binding) {
            val dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(item.dt),
                ZoneId.systemDefault()
            )

            val formattedTime = dateTime.format(DateTimeFormatter.ofPattern("h:mm a"))

            cityTimeItem?.text = formattedTime
            temperatureItem?.text = item.temp
            iconWeatherItem?.loadUrl("https://openweathermap.org/img/wn/${item.icon}.png")
        }
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<ForecastItemFiveResponse>() {
    override fun areItemsTheSame(
        oldItem: ForecastItemFiveResponse, newItem: ForecastItemFiveResponse,
    ): Boolean = oldItem.dt == newItem.dt

    override fun areContentsTheSame(
        oldItem: ForecastItemFiveResponse,
        newItem: ForecastItemFiveResponse
    ): Boolean {
        return oldItem == newItem
    }
}