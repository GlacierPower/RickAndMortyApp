package com.glacierpower.domain.local

import androidx.paging.PagingData
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


}