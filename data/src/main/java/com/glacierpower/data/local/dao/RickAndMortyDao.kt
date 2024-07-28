package com.glacierpower.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glacierpower.data.local.entity.CharacterEntity
import com.glacierpower.data.local.entity.LocationEntity

@Dao
interface RickAndMortyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacter(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characterEntity WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Int): CharacterEntity?

    @Query("SELECT * FROM characterEntity")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocation(location: List<LocationEntity>)

    @Query("SELECT * FROM locationEntity WHERE id = :locationId")
    suspend fun getLocationById(locationId: Int): LocationEntity?

    @Query("SELECT * FROM locationEntity")
    suspend fun getAllLocation(): List<LocationEntity>

}