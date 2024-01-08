package com.example.usecases.home

import com.example.domain.common.Errors.Companion.connectionErrorCode
import com.example.lib.model.repository.WeatherRepository
import com.example.lib.model.response.WeatherForecastResponse
import com.example.lib.model.response.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
class GetForecastUseCase(
    private val repository: WeatherRepository
) {
    operator fun invoke(latitude: Double, longitude: Double): Flow<Resource<WeatherForecastResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                emit(Resource.Success(repository.getForecastWeather(latitude, longitude)))
            } catch (e: HttpException) {
                emit(Resource.Error(e.code().toString()))
            } catch (e: IOException) {
                emit(Resource.Error(connectionErrorCode.toString()))
            }
        }
}