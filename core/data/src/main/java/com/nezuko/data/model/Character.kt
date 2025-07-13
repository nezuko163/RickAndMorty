package com.nezuko.data.model

import com.nezuko.data.source.LocationInfo
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationInfo,
    val location: LocationInfo,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) {
    val color = when (status.lowercase()) {
        "alive" -> StatusColor.ALIVE
        "dead" -> StatusColor.DEAD
        else -> StatusColor.ELSE
    }

    enum class StatusColor {
        ALIVE, DEAD, ELSE
    }
}