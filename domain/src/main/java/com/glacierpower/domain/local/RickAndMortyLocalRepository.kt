package com.glacierpower.domain.local

import androidx.paging.PagingData
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.domain.model.LocationResultModel
import com.glacierpower.domain.model.ResultsModel
import kotlinx.coroutines.flow.Flow

interface RickAndMortyLocalRepository {

    suspend fun insertCharacterData()

    suspend fun getCharacterFromDbById(id:Int): ResultsModel

    suspend fun getAllCharactersFromDb(
        status: String?,
        gender: String?,
        name: String?
    ): Flow<PagingData<ResultsModel>>

    suspend fun insertLocationData()

    suspend fun getLocationFromDbById(id:Int): LocationResultModel

    suspend fun getAllLocationFromDb(
        name:String?,
        type:String?,
        dimension:String?
    ): Flow<PagingData<LocationResultModel>>

    suspend fun insertEpisodeData()

    suspend fun getEpisodeFromDbById(id:Int): EpisodeModel

    suspend fun getAllEpisodeFromDb(
        name:String?,
        episode:String?
    ): Flow<PagingData<EpisodeModel>>


}