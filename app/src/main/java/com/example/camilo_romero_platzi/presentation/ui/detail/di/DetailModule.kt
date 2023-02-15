package com.example.camilo_romero_platzi.presentation.ui.detail.di

import com.example.lib.model.repository.MarvelRepository
import com.example.usecase.s.detail_section.GetCharacterDetailSectionUseCase
import com.example.usecase.s.detail.GetCharacterDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailModule {

    @Provides
    @ViewModelScoped
    fun provideGetCharacterDetailUseCase(marvelRepository: MarvelRepository): GetCharacterDetailUseCase =
        GetCharacterDetailUseCase(marvelRepository)

    @Provides
    @ViewModelScoped
    fun providesGetCharacterDetailSectionUseCase(marvelRepository: MarvelRepository): GetCharacterDetailSectionUseCase =
        GetCharacterDetailSectionUseCase(marvelRepository)
}