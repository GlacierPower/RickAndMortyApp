package com.glacierpower.domain

import androidx.paging.PagingData
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.domain.model.LocationResultModel
import com.glacierpower.domain.model.ResultsModel
import kotlinx.coroutines.flow.Flow


interface RickAndMortyRepository {

    suspend fun getCharactersData(
        status: String,
        gender: String,
        name: String = ""
    ): Flow<PagingData<ResultsModel>>

    suspend fun getCharacterById(id:Int):ResultsModel

    suspend fun getEpisodeById(id:Int):EpisodeModel

    suspend fun getAllEpisode(
        name:String,
        episode:String
    ): Flow<PagingData<EpisodeModel>>

    suspend fun getAllLocation(
        name:String,
        type:String,
        dimension:String
    ):Flow<PagingData<LocationResultModel>>
}