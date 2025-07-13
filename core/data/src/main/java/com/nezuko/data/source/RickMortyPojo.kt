package com.nezuko.data.source

import com.nezuko.data.model.Character
import com.nezuko.data.util.getPageFromUrl
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    val info: PageInfo,
    val results: List<Character>
)

@Serializable
data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
) {
    var nextPage = next?.let { getPageFromUrl(it) }
    var prevPage = prev?.let { getPageFromUrl(it) }
}

@Serializable
data class LocationInfo(
    val name: String,
    val url: String
)

@Serializable
data class ErrorResponse(val error: String)