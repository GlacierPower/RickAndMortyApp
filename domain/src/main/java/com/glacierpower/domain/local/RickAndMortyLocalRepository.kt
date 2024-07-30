package com.glacierpower.domain.local

import androidx.paging.PagingData
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.domain.model.LocationResultModel
import com.glacierpower.domain.model.ResultsModel
import kotlinx.coroutines.flow.Flow

interface RickAndMortyLocalRepository {

    suspend fun insertCharacterData()

    suspend fun getCharacterFromDbById(id: Int): ResultsModel

    suspend fun getAllCharactersFromDb(
        name: String? = null,
        gender: String? = null,
        status: String? = null,
    ): Flow<PagingData<ResultsModel>>

    suspend fun insertLocationData()

    suspend fun getLocationFromDbById(id: Int): LocationResultModel

    suspend fun getAllLocationFromDb(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): Flow<PagingData<LocationResultModel>>

    suspend fun insertEpisodeData()

    suspend fun getEpisodeFromDbById(id: Int): EpisodeModel

    suspend fun getAllEpisodeFromDb(
        name: String? = null,
        episode: String? = null
    ): Flow<PagingData<EpisodeModel>>


}