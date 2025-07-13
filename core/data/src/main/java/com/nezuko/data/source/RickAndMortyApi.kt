package com.nezuko.data.source

import android.util.Log
import com.nezuko.data.model.Character
import com.nezuko.data.model.ResultModel
import com.nezuko.data.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class RickAndMortyApi @Inject constructor(
    private val client: HttpClient
) {
    private val TAG: String = "RickAndMortyApi"

    suspend fun getCharactersFlow(
        page: Int?,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): ResultModel<CharacterResponse> = safeRequest {
        val a = client.get(CHARACTERS_URL) {
            parameter("page", page)
            parameter("name", name)
            parameter("status", status)
            parameter("species", species)
            parameter("type", type)
            parameter("gender", gender)
        }.body<CharacterResponse>()
        Log.i(TAG, "getCharactersFlow: $a")
        a
    }

    suspend fun getCharacterByIdFlow(id: Int): ResultModel<Character> = safeRequest {
        client.get("$CHARACTERS_URL/$id").body()
    }

    companion object {
        private const val CHARACTERS_URL = "https://rickandmortyapi.com/api/character"
    }
}