package com.glacierpower.domain

import androidx.paging.PagingData
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.domain.model.LocationResultModel
import com.glacierpower.domain.model.ResultsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickAndMortyInteractor @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    suspend fun getCharactersData(
        status: String,
        gender: String,
        name: String = ""
    ): Flow<PagingData<ResultsModel>> {
        return rickAndMortyRepository.getCharactersData(status, gender, name)
    }

    suspend fun getCharacterById(id: Int): ResultsModel {
        return rickAndMortyRepository.getCharacterById(id)
    }

    suspend fun getEpisodeById(id: Int): EpisodeModel {
        return rickAndMortyRepository.getEpisodeById(id)
    }

    suspend fun getAllEpisode(
        name: String,
        episode: String
    ): Flow<PagingData<EpisodeModel>> {
        return rickAndMortyRepository.getAllEpisode(name, episode)
    }

    suspend fun getAllLocation(
        name: String,
        type: String,
        dimension: String
    ): Flow<PagingData<LocationResultModel>> {
        return rickAndMortyRepository.getAllLocation(name, type, dimension)
    }

    suspend fun getLocationById(id:Int):LocationResultModel{
        return rickAndMortyRepository.getLocationById(id)
    }
}