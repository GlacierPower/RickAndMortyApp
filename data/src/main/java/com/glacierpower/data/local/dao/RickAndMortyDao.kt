package com.glacierpower.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glacierpower.data.local.entity.CharacterEntity

@Dao
interface RickAndMortyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacter(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characterEntity WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Int): CharacterEntity?

    @Query("SELECT * FROM characterEntity")
    suspend fun getAllCharacters(): List<CharacterEntity>

}