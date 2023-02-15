package com.example.camilo_romero_platzi.presentation.ui.home.di

import com.example.lib.model.repository.MarvelRepository
import com.example.usecase.s.home.GetCharactersUseCase
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
    fun provideGetCharactersUseCase(repository: MarvelRepository): GetCharactersUseCase =
        GetCharactersUseCase(repository)
}