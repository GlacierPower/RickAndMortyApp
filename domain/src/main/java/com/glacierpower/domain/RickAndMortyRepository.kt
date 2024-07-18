package com.glacierpower.domain

import androidx.paging.PagingData
import com.glacierpower.domain.model.ResultsModel
import kotlinx.coroutines.flow.Flow


interface RickAndMortyRepository {

    suspend fun getCharactersData(
        status: String,
        gender: String,
        name: String = ""
    ): Flow<PagingData<ResultsModel>>

    suspend fun getCharacterById(id:Int):ResultsModel
}