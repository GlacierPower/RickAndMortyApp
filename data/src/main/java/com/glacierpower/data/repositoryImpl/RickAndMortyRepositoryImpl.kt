package com.glacierpower.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.glacierpower.data.local.dao.RickAndMortyDao
import com.glacierpower.data.paging.CharacterPagingSource
import com.glacierpower.data.remote.RickAndMortyService
import com.glacierpower.data.utils.GenderState
import com.glacierpower.data.utils.StatusState
import com.glacierpower.domain.RickAndMortyRepository
import com.glacierpower.domain.model.ResultsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RickAndMortyRepositoryImpl(
    private val rickAndMortyApi: RickAndMortyService,
    private val rickAndMortyDao: RickAndMortyDao
) : RickAndMortyRepository {
    override suspend fun getAllCharacters(
        status: StatusState,
        gender: GenderState,
        name: String,
        page: Int
    ): Result<Flow<PagingData<ResultsModel>>> = makeApiCall(Dispatchers.IO) {
        Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = {
                CharacterPagingSource(
                    rickAndMortyApi,
                    statusState = status,
                    genderState = gender,
                    name = name
                )
            }
        ).flow
    }
}


private suspend fun <T> makeApiCall(
    dispatcher: CoroutineDispatcher,
    call: suspend () -> T
): Result<T> = runCatching {
    withContext(dispatcher) {
        call.invoke()
    }

}