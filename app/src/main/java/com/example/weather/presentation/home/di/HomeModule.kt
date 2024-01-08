package com.example.weather.presentation.home.di

import com.example.lib.model.repository.WeatherRepository
import com.example.usecases.home.GetForecastUseCase
import com.example.usecases.home.GetLocationsByCoordinatesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class HomeModule {

    @Provides
    @ViewModelScoped
    fun provideGetLocationsByCoordinates(repository: WeatherRepository): GetLocationsByCoordinatesUseCase =
        GetLocationsByCoordinatesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideForecast(repository: WeatherRepository): GetForecastUseCase =
        GetForecastUseCase(repository)
}