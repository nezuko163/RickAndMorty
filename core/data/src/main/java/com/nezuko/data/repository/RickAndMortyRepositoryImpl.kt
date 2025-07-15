package com.nezuko.data.repository

import com.nezuko.data.db.CharacterDao
import com.nezuko.data.db.toEntity
import com.nezuko.data.db.toModel
import com.nezuko.data.model.Character
import com.nezuko.data.model.ResultModel
import com.nezuko.data.source.CharacterResponse
import com.nezuko.data.source.PageInfo
import com.nezuko.data.source.RickAndMortyApi
import com.nezuko.data.source.Translator
import com.nezuko.data.util.networkQueryWithCache
import com.nezuko.data.util.safeRoomRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private val TAG = "RickAndMortyRepositoryImpl"

class RickAndMortyRepositoryImpl @Inject constructor(
    private val dao: CharacterDao,
    private val api: RickAndMortyApi,
    private val translator: Translator
) : RickAndMortyRepository {
    override suspend fun getCharacters(
        page: Int?,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): ResultModel<CharacterResponse> {
        val a = networkQueryWithCache(
            queryCache = {
                getCharactersFromCachePage(name, status, species, type, gender, page ?: 1)
            },
            fetchNetwork = {
                api.getCharactersFlow(page, name, status, species, type, gender)
            },
            saveFetch = { characterResponse ->
                dao.insertAll(characterResponse.results.map { it.toEntity() })
            }
        )

        return if (a is ResultModel.Success) {
            a.copy(data = a.data.copy(results = a.data.results.map { it.translate(translator::translate) }))
        } else {
            a
        }
    }

    override fun getCharacterById(id: Int): Flow<ResultModel<Character>> =
        flow {
            emit(ResultModel.loading())
            val a = networkQueryWithCache(
                queryCache = { safeRoomRequest { dao.getById(id).toModel() } },
                fetchNetwork = { api.getCharacterByIdFlow(id) },
                isCacheFirst = true
            )
            if (a is ResultModel.Success) {
                emit(value = a.copy(a.data.translate(translator::translate).also {
                }))
            } else {
                emit(a)
            }
        }

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