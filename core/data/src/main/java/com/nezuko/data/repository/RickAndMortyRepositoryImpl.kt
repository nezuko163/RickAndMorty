package com.nezuko.data.repository

import android.R.attr.name
import android.util.Log
import com.nezuko.data.db.CharacterDao
import com.nezuko.data.db.toEntity
import com.nezuko.data.db.toModel
import com.nezuko.data.model.ResultModel
import com.nezuko.data.model.Character
import com.nezuko.data.source.CharacterResponse
import com.nezuko.data.source.PageInfo
import com.nezuko.data.source.RickAndMortyApi
import com.nezuko.data.util.networkQueryWithCache
import com.nezuko.data.util.safeRoomFlow
import com.nezuko.data.util.safeRoomRequest
import io.ktor.client.utils.EmptyContent.status
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private val TAG = "RickAndMortyRepositoryImpl"

class RickAndMortyRepositoryImpl @Inject constructor(
    private val dao: CharacterDao,
    private val api: RickAndMortyApi
) : RickAndMortyRepository {
    override suspend fun getCharacters(
        page: Int?,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): ResultModel<CharacterResponse>  {
        return networkQueryWithCache(
            queryCache = {
                Log.i(TAG, "getCharacters: queryCache")
                getCharactersFromCachePage(name, status, species, type, gender, page ?: 1)
            },
            fetchNetwork = {
                Log.i(TAG, "getCharacters: fetchNetwork")
                api.getCharactersFlow(page, name, status, species, type, gender)
            },
            saveFetch = { characterResponse ->
                Log.i(TAG, "getCharacters: saveFetch")
                dao.insertAll(characterResponse.results.map { it.toEntity() })
            }
        ).also {
            Log.i(TAG, "getCharacters: res - $it")
        }
    }

    override suspend fun getCharacterById(id: Int): Flow<ResultModel<Character>> =
        safeRoomFlow { dao.getById(id).toModel() }

    suspend fun getCharactersFromCachePage(
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?,
        page: Int = 1,
        pageSize: Int = 20,
    ): ResultModel<CharacterResponse> = safeRoomRequest {
        val totalCount = dao.getCharactersCount()
        val totalPages = (totalCount + pageSize - 1) / pageSize
        val offset = (page - 1) * pageSize
        val results = dao.getFilteredPaginatedCharacters(
            limit = pageSize,
            offset = offset,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender
        ).map { it.toModel() }

        val pageInfo = PageInfo(
            count = totalCount,
            pages = totalPages,
            next = if (page < totalPages) "page=${page + 1}" else null,
            prev = if (page > 1) "page=${page - 1}" else null
        ).apply {
            nextPage = if (page < totalPages) page + 1 else null
            prevPage = if (page > 1) page - 1 else null
        }
        CharacterResponse(
            pageInfo, results
        )
    }
}