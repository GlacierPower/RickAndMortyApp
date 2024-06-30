package com.glacierpower.domain


import androidx.paging.PagingData
import com.glacierpower.data.utils.GenderState
import com.glacierpower.data.utils.StatusState
import com.glacierpower.domain.model.ResultsModel
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {

    suspend fun getAllCharacters(
        status: StatusState,
        gender: GenderState,
        name: String,
        page: Int
    ): Result<Flow<PagingData<ResultsModel>>>
}