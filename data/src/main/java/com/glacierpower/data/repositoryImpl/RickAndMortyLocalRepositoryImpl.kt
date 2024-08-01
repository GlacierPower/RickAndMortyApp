package com.glacierpower.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.glacierpower.data.local.dao.RickAndMortyDao
import com.glacierpower.data.mappers.character.toEntity
import com.glacierpower.data.mappers.character.toModel
import com.glacierpower.data.mappers.episode.toEntity
import com.glacierpower.data.mappers.episode.toModel
import com.glacierpower.data.mappers.loaction.toEntity
import com.glacierpower.data.mappers.loaction.toModel
import com.glacierpower.data.paging.charcter.CharacterPagingSourceDB
import com.glacierpower.data.paging.episode.EpisodePagingSourceDB
import com.glacierpower.data.paging.location.LocationPagingSourceDB
import com.glacierpower.data.remote.RickAndMortyService
import com.glacierpower.domain.local.RickAndMortyLocalRepository
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.domain.model.LocationResultModel
import com.glacierpower.domain.model.ResultsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RickAndMortyLocalRepositoryImpl @Inject constructor(
    private val rickAndMortyService: RickAndMortyService,
    private val rickAndMortyDao: RickAndMortyDao
) : RickAndMortyLocalRepository {
    override suspend fun insertCharacterData() {
        return withContext(Dispatchers.IO) {
                val characterData = rickAndMortyService.getCharacter()
                rickAndMortyDao.insertAllCharacter(characterData.results.map { it.toEntity() })

        }
    }

    override suspend fun getCharacterFromDbById(id: Int): ResultsModel {
        return withContext(Dispatchers.IO) {
            rickAndMortyDao.getCharacterById(id)?.toModel() ?: ResultsModel()
        }
    }

    override suspend fun getAllCharactersFromDb(
        name: String?,
        gender: String?,
        status: String?,
    ): Flow<PagingData<ResultsModel>> {
        return withContext(Dispatchers.IO) {
            Pager(
                config = PagingConfig(pageSize = 25),
                pagingSourceFactory = {
                    CharacterPagingSourceDB(
                        rickAndMortyDao, name = name, status = status, gender = gender
                    )
                }
            ).flow

        }
    }

    override suspend fun insertLocationData() {
        val locationData = rickAndMortyService.getAllLocation()
        return withContext(Dispatchers.IO) {
            rickAndMortyDao.insertAllLocation(locationData.results.map { it.toEntity() })
        }
    }

    override suspend fun getLocationFromDbById(id: Int): LocationResultModel {
        return withContext(Dispatchers.IO) {
            rickAndMortyDao.getLocationById(id)!!.toModel()
        }
    }

    override suspend fun getAllLocationFromDb(
        name: String?,
        type: String?,
        dimension: String?
    ): Flow<PagingData<LocationResultModel>> {
        return withContext(Dispatchers.IO) {
            Pager(
                config = PagingConfig(pageSize = 25),
                pagingSourceFactory = {
                    LocationPagingSourceDB(
                        rickAndMortyDao, name = name, type = type, dimension = dimension
                    )
                }
            )
        }.flow
    }

    override suspend fun insertEpisodeData() {
        val response = rickAndMortyService.getAllEpisode()
        return withContext(Dispatchers.IO) {
            rickAndMortyDao.insertAllEpisode(response.results.map { it.toEntity() })
        }
    }

    override suspend fun getEpisodeFromDbById(id: Int): EpisodeModel {
        return withContext(Dispatchers.IO) {
            rickAndMortyDao.getEpisodeById(id)!!.toModel()
        }
    }

    override suspend fun getAllEpisodeFromDb(
        name: String?,
        episode: String?
    ): Flow<PagingData<EpisodeModel>> {
        return withContext(Dispatchers.IO) {
            Pager(
                config = PagingConfig(pageSize = 25),
                pagingSourceFactory = {
                    EpisodePagingSourceDB(
                        rickAndMortyDao, name = name, episode = episode
                    )
                }
            )
        }.flow
    }

}