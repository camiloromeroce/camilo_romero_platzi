package com.example.camilo_romero_platzi.presentation.ui.detail_section.state

import com.example.domain.model.detail_section.CharacterDetailSectionItem

sealed class CharacterDetailsSectionState {
    object Loading : CharacterDetailsSectionState()

    data class LoadSectionDetails(val details: List<CharacterDetailSectionItem>) :
        CharacterDetailsSectionState()

    data class Error(val errorMessage: String) : CharacterDetailsSectionState()
}