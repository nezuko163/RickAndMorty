package com.nezuko.data.source

import android.util.Log
import com.nezuko.data.exceptions.EmptyResultException
import com.nezuko.data.model.Character
import com.nezuko.data.model.ResultModel
import com.nezuko.data.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
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
    ): ResultModel<CharacterResponse> = safeRequest  {
        val response: HttpResponse = client.get(CHARACTERS_URL) {
            parameter("page", page)
            parameter("name", name)
            parameter("status", status)
            parameter("species", species)
            parameter("type", type)
            parameter("gender", gender)
            expectSuccess = false
        }

        val text = response.bodyAsText()

        val json = Json { ignoreUnknownKeys = true }
        val element: JsonElement = json.parseToJsonElement(text)

        element.jsonObject["error"]?.jsonPrimitive?.content?.let { errorMessage ->
            throw EmptyResultException("Нет результата")
        }

        json.decodeFromJsonElement(element)
    }

    suspend fun getCharacterByIdFlow(id: Int): ResultModel<Character> = safeRequest {
        client.get("$CHARACTERS_URL/$id").body()
    }

    companion object {
        private const val CHARACTERS_URL = "https://rickandmortyapi.com/api/character"
    }
}