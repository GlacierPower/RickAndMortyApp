package com.glacierpower.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.glacierpower.data.local.dao.RickAndMortyDao
import com.glacierpower.data.mappers.character.toEntity
import com.glacierpower.data.mappers.character.toModel
import com.glacierpower.data.mappers.loaction.toEntity
import com.glacierpower.data.mappers.loaction.toModel
import com.glacierpower.data.paging.charcter.CharacterPagingSourceDB
import com.glacierpower.data.paging.location.LocationPagingSourceDB
import com.glacierpower.data.remote.RickAndMortyService
import com.glacierpower.domain.local.RickAndMortyLocalRepository
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
        val characterData = rickAndMortyService.getCharacter()
        return withContext(Dispatchers.IO) {
            rickAndMortyDao.insertAllCharacter(characterData.results.map { it.toEntity() })
        }
    }

    override suspend fun getCharacterFromDbById(id: Int): ResultsModel {
        return withContext(Dispatchers.IO) {
            rickAndMortyDao.getCharacterById(id)!!.toModel()
        }
    }

    override suspend fun getAllCharactersFromDb(
        status: String?,
        gender: String?,
        name: String?
    ): Flow<PagingData<ResultsModel>> {
        return withContext(Dispatchers.IO) {
            Pager(
                config = PagingConfig(pageSize = 25),
                pagingSourceFactory = {
                    CharacterPagingSourceDB(
                        rickAndMortyDao
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
                        rickAndMortyDao
                    )
                }
            )
        }.flow
    }

}