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
        status: String?,
        gender: String?,
        name: String?
    ): Flow<PagingData<ResultsModel>> =
        rickAndMortyLocalRepository.getAllCharactersFromDb(status, gender, name)

    suspend fun insertLocationData() {
        rickAndMortyLocalRepository.insertLocationData()
    }

    suspend fun getLocationFromDbById(id: Int): LocationResultModel {
        return rickAndMortyLocalRepository.getLocationFromDbById(id)
    }

    suspend fun getAllLocationFromDb(
        name: String?,
        type: String?,
        dimension: String?
    ): Flow<PagingData<LocationResultModel>> {
        return rickAndMortyLocalRepository.getAllLocationFromDb(name, type, dimension)
    }

    suspend fun insertEpisodeData() {
        rickAndMortyLocalRepository.insertEpisodeData()
    }

    suspend fun getEpisodeFromDbById(id: Int): EpisodeModel {
        return rickAndMortyLocalRepository.getEpisodeFromDbById(id)
    }

    suspend fun getAllEpisodeFromDb(
        name: String?,
        episode: String?
    ): Flow<PagingData<EpisodeModel>> {
        return rickAndMortyLocalRepository.getAllEpisodeFromDb(name, episode)
    }

}