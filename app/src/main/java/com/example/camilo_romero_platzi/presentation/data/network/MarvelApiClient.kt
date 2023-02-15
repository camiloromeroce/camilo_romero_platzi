package com.example.camilo_romero_platzi.presentation.data.network

import com.example.lib.model.detail_section.CharacterDetailSectionObjectModel
import com.example.lib.model.details.CharacterDetailObjectModel
import com.example.lib.model.home.CharacterObjectModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApiClient {

    @GET("v1/public/characters")
    suspend fun getCharacters(): Response<CharacterObjectModel>

    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacterDetails(
        @Path("characterId") characterId: String,
    ): Response<CharacterDetailObjectModel>

    @GET("v1/public/characters/{characterId}/{section}")
    suspend fun getCharacterDetailSection(
        @Path("characterId") characterId: String,
        @Path("section") section: String,
    ): Response<CharacterDetailSectionObjectModel>
}