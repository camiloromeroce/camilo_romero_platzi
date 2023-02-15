package com.example.usecase.s.detail

import com.example.domain.home.common.Resource
import com.example.domain.home.common.connectionErrorCode
import com.example.domain.model.detail.CharacterDetailItem
import com.example.lib.model.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCharacterDetailUseCase(private val marvelRepository: MarvelRepository) {

    operator fun invoke(characterId: String): Flow<Resource<List<CharacterDetailItem>>> = flow {
        try {
            emit(Resource.Loading(emptyList()))
            emit(Resource.Success(marvelRepository.getCharacterDetail(characterId)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.code().toString(), emptyList()))
        } catch (e: IOException) {
            emit(Resource.Error(connectionErrorCode.toString(), emptyList()))
        }
    }
}