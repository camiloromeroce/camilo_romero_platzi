package com.example.weather.presentation.di

import com.example.lib.model.source.RemoteDataSource
import com.example.weather.presentation.data.network.WeatherService
import com.example.weather.presentation.data.network.source.RemoteDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideRemoteDataSource(weatherService: WeatherService): RemoteDataSource =
        RemoteDataSourceImp(weatherService)
}