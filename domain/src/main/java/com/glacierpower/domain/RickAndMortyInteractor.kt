package com.glacierpower.domain

import androidx.paging.PagingData
import com.glacierpower.data.utils.GenderState
import com.glacierpower.data.utils.StatusState
import com.glacierpower.domain.model.ResultsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickAndMortyInteractor @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) {

    suspend fun getAllCharacters(
        status: StatusState,
        gender: GenderState,
        name: String,
        page: Int
    ): Result<Flow<PagingData<ResultsModel>>> {
        return rickAndMortyRepository.getAllCharacters(
            status, gender, name, page
        )
    }
}