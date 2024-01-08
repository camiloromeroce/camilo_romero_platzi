package com.example.weather.presentation.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lib.model.response.WeatherItem
import com.example.weather.R
import com.example.weather.databinding.ItemViewWeatherLandingBinding
import com.example.weather.presentation.inflate
import com.example.weather.presentation.loadUrl

class WeatherAdapter(private val listener: (WeatherItem) -> Unit) :
    ListAdapter<WeatherItem, WeatherAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_view_weather_landing, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherItem = getItem(position)
        holder.bind(weatherItem)
        holder.itemView.apply { setOnClickListener { listener(weatherItem) } }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemViewWeatherLandingBinding =
            ItemViewWeatherLandingBinding.bind(view)

        fun bind(item: WeatherItem) = with(binding) {
            cityTimeItem?.text = item.main
            temperatureItem?.text = item.description
            iconWeatherItem?.loadUrl("https://openweathermap.org/img/wn/${item.icon}.png")
        }
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<WeatherItem>() {
    override fun areItemsTheSame(
        oldItem: WeatherItem, newItem: WeatherItem,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
        return oldItem == newItem
    }
}