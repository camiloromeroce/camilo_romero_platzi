package com.example.camilo_romero_platzi.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.camilo_romero_platzi.presentation.ui.home.state.HomeState
import com.example.domain.home.common.Resource
import com.example.domain.home.common.notCharactersErrorCode
import com.example.domain.home.common.unknownErrorCode
import com.example.domain.model.home.CharacterItem
import com.example.usecase.s.home.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state

    private val _currentCharacters: MutableList<CharacterItem> =
        emptyList<CharacterItem>().toMutableList()
    val currentCharacters = _currentCharacters

    fun getCharacters() = viewModelScope.launch {
        getCharactersUseCase().collect { result ->
            when (result) {
                is Resource.Loading -> setState(HomeState.Loading)
                is Resource.Error -> setErrorState(result.message)
                is Resource.Success -> result.data?.let { characters ->
                    if (characters.isEmpty()) setErrorState(notCharactersErrorCode.toString())
                    else setState(HomeState.LoadCharacters(characters))
                } ?: setErrorState(notCharactersErrorCode.toString())
            }
        }
    }

    fun setCurrentCharacters(filterCharacters: List<CharacterItem>) {
        if (_currentCharacters.isEmpty())
            _currentCharacters.addAll(filterCharacters)
        else {
            _currentCharacters.clear()
            _currentCharacters.addAll(filterCharacters)
        }
    }

    private fun setState(state: HomeState) {
        _state.value = state
    }

    private fun setErrorState(errorMessage: String?) {
        setState(HomeState.Error(errorMessage ?: unknownErrorCode.toString()))
    }
}