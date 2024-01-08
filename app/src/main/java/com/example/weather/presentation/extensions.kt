package com.example.weather.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.domain.common.DataTypes
import com.example.weather.R
import com.example.domain.common.Errors.Companion.connectionErrorCode
import com.example.domain.common.Errors.Companion.notFiltersErrorCode
import com.example.domain.common.Errors.Companion.notLocationsErrorCode
import com.example.domain.common.Errors.Companion.notWeatherStateErrorCode
import com.example.domain.common.Errors.Companion.unknownErrorCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun Fragment.getResourceView(resourceId: Int): View =
    requireActivity().findViewById(resourceId)

fun Fragment.onBackPressedCustomAction(action: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override
            fun handleOnBackPressed() {
                action()
            }
        })
}

fun <T> LifecycleOwner.launchAndCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit,
) {
    lifecycleScope.launch {
        this@launchAndCollect.repeatOnLifecycle(state) {
            flow.collect(body)
        }
    }
}

fun Fragment.getErrorMessage(errorCode: String): String {
    return when (errorCode.toInt()) {
        connectionErrorCode -> getString(R.string.noConnectionMessage)
        notLocationsErrorCode -> getString(R.string.notLocationsMessage)
        notWeatherStateErrorCode -> getString(R.string.notWeatherStateMessage)
        notFiltersErrorCode -> getString(R.string.notFiltersMessage)
        unknownErrorCode -> getString(R.string.unknownMessage)
        else -> getString(R.string.unknownMessage)
    }
}

fun <T> formatValue(value: T, type: DataTypes): String {
    val speedConverter = 1.6093440006147
    return when (type) {
        DataTypes.TEMPERATURE -> DecimalFormat("#ºC").format(value)
        DataTypes.PERCENT -> "${value}%"
        DataTypes.SPEED -> {
            val convertedValue = (value as Double) * speedConverter
            DecimalFormat("#,## km/h").format(convertedValue)
        }
        DataTypes.DATE -> {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(value.toString())
            SimpleDateFormat("EEEE", Locale.getDefault()).format(date).replaceFirstChar {
                it.titlecase()
            }
        }
    }
}

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)