package com.example.usecase.s.detail_section

import com.example.domain.home.common.Resource
import com.example.domain.home.common.connectionErrorCode
import com.example.domain.model.detail_section.CharacterDetailSectionItem
import com.example.lib.model.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCharacterDetailSectionUseCase(private val repository: MarvelRepository) {
    operator fun invoke(
        characterId: String,
        section: String,
    ): Flow<Resource<List<CharacterDetailSectionItem>>> = flow {
        try {
            emit(Resource.Loading(emptyList()))
            emit(
                Resource.Success(
                    repository.getCharacterDetailSection(
                        characterId = characterId, section = section
                    )
                )
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.code().toString(), emptyList()))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    connectionErrorCode.toString(), emptyList()
                )
            )
        }
    }
}