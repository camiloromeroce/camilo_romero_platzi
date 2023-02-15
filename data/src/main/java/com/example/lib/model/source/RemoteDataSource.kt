package com.example.lib.model.source

import com.example.domain.model.detail.CharacterDetailItem
import com.example.domain.model.detail_section.CharacterDetailSectionItem
import com.example.domain.model.home.CharacterItem

interface RemoteDataSource {

    suspend fun getCharacters(): List<CharacterItem>

    suspend fun getCharacterDetails(characterId: String): List<CharacterDetailItem>

    suspend fun getCharacterDetailSection(
        characterId: String, section: String
    ): List<CharacterDetailSectionItem>
}