package com.nezuko.data.repository

import com.nezuko.data.model.ResultModel
import com.nezuko.data.model.Character
import com.nezuko.data.source.CharacterResponse
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    suspend fun getCharacters(
        page: Int? = null,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): ResultModel<CharacterResponse>

    suspend fun getCharacterById(id: Int): Flow<ResultModel<Character>>
}