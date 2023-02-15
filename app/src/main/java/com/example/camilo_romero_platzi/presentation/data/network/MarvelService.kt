package com.example.camilo_romero_platzi.presentation.data.network

import com.example.lib.model.detail_section.CharacterDetailSectionObjectModel
import com.example.lib.model.details.CharacterDetailObjectModel
import com.example.lib.model.home.CharacterObjectModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarvelService @Inject constructor(private val apiClient: MarvelApiClient) {

    suspend fun getCharacters(): CharacterObjectModel? = withContext(Dispatchers.IO) {
        apiClient.getCharacters().body()
    }

    suspend fun getDetailCharacter(characterId: String): CharacterDetailObjectModel? =
        withContext(Dispatchers.IO) {
            apiClient.getCharacterDetails(characterId = characterId).body()
        }

    suspend fun getCharacterDetailSection(
        characterId: String, section: String
    ): CharacterDetailSectionObjectModel? = withContext(Dispatchers.IO) {
        apiClient.getCharacterDetailSection(
            characterId = characterId, section = section
        ).body()
    }
}