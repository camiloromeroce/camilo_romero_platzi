package com.example.weather.presentation

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weather.R
import com.example.domain.common.Errors.Companion.connectionErrorCode
import com.example.domain.common.Errors.Companion.notFiltersErrorCode
import com.example.domain.common.Errors.Companion.notLocationsErrorCode
import com.example.domain.common.Errors.Companion.notWeatherStateErrorCode
import com.example.domain.common.Errors.Companion.unknownErrorCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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