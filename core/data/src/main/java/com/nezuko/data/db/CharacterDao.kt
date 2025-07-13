package com.nezuko.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query(
        """
        SELECT * FROM characters
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%')
          AND (:status IS NULL OR status = :status)
          AND (:species IS NULL OR species = :species)
          AND (:type IS NULL OR type = :type)
          AND (:gender IS NULL OR gender = :gender)
        ORDER BY id
        LIMIT :limit OFFSET :offset
    """
    )
    suspend fun getFilteredPaginatedCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null,
        limit: Int, offset: Int = 0
    ): List<CharacterEntity>

    @Query("SELECT COUNT(*) FROM characters")
    suspend fun getCharactersCount(): Int

    @Query("SELECT * FROM characters ORDER BY id")
    fun getAllFlow(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): CharacterEntity

    @Query("SELECT * FROM characters ORDER BY id")
    fun getAll(): List<CharacterEntity>

    // посмотреть с ignore
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity)
}

