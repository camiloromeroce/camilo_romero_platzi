package com.example.camilo_romero_platzi.presentation.ui.common

import androidx.navigation.NavController
import com.example.camilo_romero_platzi.presentation.ui.detail.DetailFragmentDirections
import com.example.camilo_romero_platzi.presentation.ui.home.HomeFragmentDirections
import com.example.domain.model.home.CharacterItem


class UiState (private val navController: NavController) {

    fun onCharacterClicked(characterItem: CharacterItem) {
        val destination =
            HomeFragmentDirections.actionNavigationHomeToNavigationDetail4(characterItem.id)
        navController.navigate(destination)
    }

    fun onSectionClicked(value: String, characterId: String) {
        val destination = DetailFragmentDirections.actionNavigationDetailToNavigationDetailSection(
            value, characterId
        )
        navController.navigate(destination)
    }
}