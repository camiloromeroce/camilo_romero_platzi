package com.example.camilo_romero_platzi.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.camilo_romero_platzi.presentation.ui.detail.state.CharacterDetailsState
import com.example.domain.home.common.Resource
import com.example.domain.home.common.notDetailsErrorCode
import com.example.domain.home.common.unknownErrorCode
import com.example.usecase.s.detail.GetCharacterDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterDetailsState>(CharacterDetailsState.Loading)
    val state: StateFlow<CharacterDetailsState> = _state

    fun getCharacterDetail(characterId: String) = viewModelScope.launch {
        getCharacterDetailUseCase(characterId).collect { result ->
            when (result) {
                is Resource.Error -> setErrorState(result.message)
                is Resource.Loading -> setState(CharacterDetailsState.Loading)
                is Resource.Success -> result.data?.let { details ->
                    if (details.isEmpty()) setErrorState(notDetailsErrorCode.toString())
                    else setState(CharacterDetailsState.LoadDetails(details))
                } ?: setErrorState(notDetailsErrorCode.toString())
            }
        }
    }

    private fun setState(state: CharacterDetailsState) {
        _state.value = state
    }

    private fun setErrorState(errorMessage: String?) {
        setState(CharacterDetailsState.Error(errorMessage ?: unknownErrorCode.toString()))
    }
}