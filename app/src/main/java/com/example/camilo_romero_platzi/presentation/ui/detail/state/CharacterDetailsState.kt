package com.example.camilo_romero_platzi.presentation.ui.detail.state

import com.example.domain.model.detail.CharacterDetailItem

sealed class CharacterDetailsState {

    object Loading : CharacterDetailsState()

    data class LoadDetails(val characterDetail: List<CharacterDetailItem>) : CharacterDetailsState()

    data class Error(val errorMessage: String) : CharacterDetailsState()
}
