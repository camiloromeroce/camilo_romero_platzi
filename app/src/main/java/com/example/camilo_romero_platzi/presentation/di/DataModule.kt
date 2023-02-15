package com.example.camilo_romero_platzi.presentation.di

import com.example.camilo_romero_platzi.presentation.data.network.MarvelService
import com.example.camilo_romero_platzi.presentation.data.source.RemoteDataSourceImpl
import com.example.lib.model.repository.MarvelRepository
import com.example.lib.model.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideRemoteDataSource(marvelService: MarvelService): RemoteDataSource =
        RemoteDataSourceImpl(marvelService)

    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource): MarvelRepository =
        MarvelRepository(remoteDataSource)

}