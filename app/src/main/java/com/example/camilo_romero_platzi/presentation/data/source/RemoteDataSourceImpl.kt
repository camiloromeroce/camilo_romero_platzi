package com.example.camilo_romero_platzi.presentation.data.source

import com.example.camilo_romero_platzi.presentation.data.network.MarvelService
import com.example.domain.model.detail.CharacterDetailItem
import com.example.domain.model.detail_section.CharacterDetailSectionItem
import com.example.domain.model.home.CharacterItem
import com.example.lib.model.mappers.toDomain
import com.example.lib.model.source.RemoteDataSource

class RemoteDataSourceImpl(private val marvelService: MarvelService) : RemoteDataSource {

    override suspend fun getCharacters(): List<CharacterItem> =
        marvelService.getCharacters()?.let { characters ->
            characters.data.results.map { it.toDomain() }
        } ?: emptyList()

    override suspend fun getCharacterDetails(characterId: String): List<CharacterDetailItem> =
        marvelService.getDetailCharacter(characterId)?.let { detailObject ->
            detailObject.data.results.map { it.toDomain() }
        } ?: emptyList()

    override suspend fun getCharacterDetailSection(
        characterId: String, section: String
    ): List<CharacterDetailSectionItem> = marvelService.getCharacterDetailSection(
        characterId = characterId, section = section
    )?.let { detailSectionObject ->
        detailSectionObject.data.results.map { it.toDomain() }
    } ?: emptyList()
}