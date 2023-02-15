package com.example.camilo_romero_platzi.presentation.ui.detail_section

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.camilo_romero_platzi.presentation.ui.detail_section.state.CharacterDetailsSectionState
import com.example.domain.home.common.Resource
import com.example.domain.home.common.notDetailsErrorCode
import com.example.domain.home.common.unknownErrorCode
import com.example.usecase.s.detail_section.GetCharacterDetailSectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailSectionViewModel @Inject constructor(
    private val getCharacterDetailSectionUseCase: GetCharacterDetailSectionUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<CharacterDetailsSectionState>(CharacterDetailsSectionState.Loading)
    val state: StateFlow<CharacterDetailsSectionState> = _state

    fun getCharacterDetailSection(
        characterId: String, section: String
    ) = viewModelScope.launch {
        getCharacterDetailSectionUseCase(
            characterId = characterId, section = section
        ).collect { result ->
            when (result) {
                is Resource.Error -> setErrorState(result.message)
                is Resource.Loading -> setState(CharacterDetailsSectionState.Loading)
                is Resource.Success -> result.data?.let { details ->
                    if (details.isEmpty()) setErrorState(notDetailsErrorCode.toString())
                    else setState(CharacterDetailsSectionState.LoadSectionDetails(details))
                } ?: setErrorState(notDetailsErrorCode.toString())
            }
        }
    }

    private fun setErrorState(errorMessage: String?) {
        setState(CharacterDetailsSectionState.Error(errorMessage ?: unknownErrorCode.toString()))
    }

    private fun setState(state: CharacterDetailsSectionState) {
        _state.value = state
    }
}