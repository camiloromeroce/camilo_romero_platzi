package com.example.camilo_romero_platzi.presentation.ui.home.state

import com.example.domain.model.home.CharacterItem

sealed class HomeState {

    object Loading: HomeState()

    data class LoadCharacters(val characters: List<CharacterItem>): HomeState()

    data class Error(val errorMessage: String): HomeState()
}