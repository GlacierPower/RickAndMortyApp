package com.glacierpower.domain.local

import androidx.paging.PagingData
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.domain.model.LocationResultModel
import com.glacierpower.domain.model.ResultsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickAndMortyLocalInteractor @Inject constructor(
    private val rickAndMortyLocalRepository: RickAndMortyLocalRepository
) {
    suspend fun insertCharacterData() = rickAndMortyLocalRepository.insertCharacterData()

    suspend fun getCharacterFromDbById(id: Int): ResultsModel =
        rickAndMortyLocalRepository.getCharacterFromDbById(id)

    suspend fun getAllCharactersFromDb(
        name: String? = null,
        gender: String? = null,
        status: String? = null,

    ): Flow<PagingData<ResultsModel>> =
        rickAndMortyLocalRepository.getAllCharactersFromDb(name = name, gender = gender, status = status)

    suspend fun insertLocationData() {
        rickAndMortyLocalRepository.insertLocationData()
    }

    suspend fun getLocationFromDbById(id: Int): LocationResultModel {
        return rickAndMortyLocalRepository.getLocationFromDbById(id)
    }

    suspend fun getAllLocationFromDb(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): Flow<PagingData<LocationResultModel>> {
        return rickAndMortyLocalRepository.getAllLocationFromDb(name =name, type = type, dimension = dimension)
    }

    suspend fun insertEpisodeData() {
        rickAndMortyLocalRepository.insertEpisodeData()
    }

    suspend fun getEpisodeFromDbById(id: Int): EpisodeModel {
        return rickAndMortyLocalRepository.getEpisodeFromDbById(id)
    }

    suspend fun getAllEpisodeFromDb(
        name: String? = null,
        episode: String? = null
    ): Flow<PagingData<EpisodeModel>> {
        return rickAndMortyLocalRepository.getAllEpisodeFromDb(name = name, episode = episode)
    }

}