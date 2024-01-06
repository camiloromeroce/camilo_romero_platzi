package com.example.weather.presentation.home.viewmodel

import android.Manifest
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Errors.Companion.notLocationsErrorCode
import com.example.usecases.home.GetLocationsByCoordinatesUseCase
import com.example.usecases.home.Resource
import com.example.weather.presentation.data.PermissionRequester
import com.example.weather.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getLocationsByCoordinatesUseCase: GetLocationsByCoordinatesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherState>(WeatherState.LoadingState)
    val state: StateFlow<WeatherState> = _state

    fun init() = viewModelScope.launch {
        setState(WeatherState.LoadingState)
        getLocationsByCoordinatesUseCase(0.0, 0.0).collect { result ->
            when (result) {
                is Resource.Error -> setErrorState(result.message)
                is Resource.Loading -> setState(WeatherState.LoadingState)
                is Resource.Success -> result.data?.let { locations ->
                    if (locations.name?.isEmpty() == true) setErrorState(notLocationsErrorCode.toString())
                }
            }
        }
    }


    fun Fragment.requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        val locationPermissionRequester = PermissionRequester(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        viewModelScope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }


    private fun setState(state: WeatherState) {
        _state.value = state
    }

    private fun setErrorState(errorCode: String?) {
        setState(WeatherState.Error(errorCode ?: "An unexpected error occured"))
    }
}