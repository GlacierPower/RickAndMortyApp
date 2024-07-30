package com.glacierpower.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glacierpower.data.local.entity.CharacterEntity
import com.glacierpower.data.local.entity.EpisodeEntity
import com.glacierpower.data.local.entity.LocationEntity

@Dao
interface RickAndMortyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacter(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characterEntity WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Int): CharacterEntity?

    @Query(
        "SELECT * FROM characterEntity " +
                "WHERE (:name IS NULL OR name LIKE '%' || :name  || '%') " +
                "AND (:gender IS NULL OR gender LIKE :gender) " +
                "AND (:status IS NULL OR status LIKE :status) " +
                "ORDER BY id ASC"
    )
    suspend fun getAllCharacters(
        name: String? = null,
        gender: String? = null,
        status: String? = null
    ): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocation(location: List<LocationEntity>)

    @Query("SELECT * FROM locationEntity WHERE id = :locationId")
    suspend fun getLocationById(locationId: Int): LocationEntity?

    @Query(
        "SELECT * FROM locationEntity " +
                "WHERE (:name IS NULL OR name LIKE '%' || :name  || '%') " +
                "AND (:dimension IS NULL OR dimension LIKE :dimension) " +
                "AND (:type IS NULL OR type LIKE :type) " +
                "ORDER BY id ASC"
    )
    suspend fun getAllLocation(
        type: String? = null,
        dimension: String? = null,
        name: String? = null
    ): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEpisode(episode: List<EpisodeEntity>)

    @Query("SELECT * FROM episodeEntity WHERE id = :episodeId")
    suspend fun getEpisodeById(episodeId: Int): EpisodeEntity?

    @Query(
        "SELECT * FROM episodeEntity " +
                "WHERE (:name IS NULL OR name LIKE '%' || :name  || '%') " +
                "AND (:episode IS NULL OR episode LIKE :episode) " +
                "ORDER BY id ASC"
    )
    suspend fun getAllEpisode(
        name: String? = null,
        episode: String? = null
    ): List<EpisodeEntity>


}