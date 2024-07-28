package com.glacierpower.domain.local

import androidx.paging.PagingData
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


}