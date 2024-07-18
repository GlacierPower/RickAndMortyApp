package com.glacierpower.domain

import androidx.paging.PagingData
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
}