package com.example.usecase.s.home

import com.example.domain.home.common.Resource
import com.example.domain.home.common.connectionErrorCode
import com.example.domain.model.home.CharacterItem
import com.example.lib.model.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCharactersUseCase(private val marvelRepository: MarvelRepository) {

    operator fun invoke(): Flow<Resource<List<CharacterItem>>> = flow {

        try {
            emit(Resource.Loading(emptyList()))
            emit(Resource.Success(marvelRepository.getCharacters()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.code().toString(), emptyList()))
        } catch (e: IOException) {
            emit(Resource.Error(connectionErrorCode.toString(), emptyList()))
        }
    }
}