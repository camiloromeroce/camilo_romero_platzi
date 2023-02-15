package com.example.lib.model.repository

import com.example.domain.model.detail.CharacterDetailItem
import com.example.domain.model.detail_section.CharacterDetailSectionItem
import com.example.domain.model.home.CharacterItem
import com.example.lib.model.source.RemoteDataSource

class MarvelRepository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getCharacters(): List<CharacterItem> = remoteDataSource.getCharacters()

    suspend fun getCharacterDetail(characterId: String): List<CharacterDetailItem> =
        remoteDataSource.getCharacterDetails(characterId)

    suspend fun getCharacterDetailSection(
        characterId: String, section: String
    ): List<CharacterDetailSectionItem> =
        remoteDataSource.getCharacterDetailSection(characterId = characterId, section = section)
}