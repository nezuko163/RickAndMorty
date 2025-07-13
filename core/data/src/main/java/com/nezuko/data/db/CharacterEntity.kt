package com.nezuko.data.db

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nezuko.data.source.LocationInfo
import com.nezuko.data.model.Character

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val originName: String,
    val originUrl: String,
    val locationName: String,
    val locationUrl: String,
    val image: String,
    val url: String,
    val created: String,
    val timestamp: Long = System.currentTimeMillis()
)

fun Character.toEntity() = CharacterEntity(
    id, name, status, species, type, gender,
    origin.name, origin.url, location.name, location.url,
    image, url, created
)


fun CharacterEntity.toModel() = Character(
    id, name, status, species, type, gender,
    LocationInfo(originName, originUrl),
    LocationInfo(locationName, locationUrl),
    image, listOf(), url, created
)