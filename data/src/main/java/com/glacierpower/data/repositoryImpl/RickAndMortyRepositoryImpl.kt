package com.glacierpower.data.repositoryImpl

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.glacierpower.data.local.dao.RickAndMortyDao
import com.glacierpower.data.paging.CharactersPagingSource
import com.glacierpower.data.remote.RickAndMortyService
import com.glacierpower.domain.RickAndMortyRepository
import com.glacierpower.domain.model.ResultsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val rickAndMortyApi: RickAndMortyService,
    private val rickAndMortyDao: RickAndMortyDao
) : RickAndMortyRepository {

    override suspend fun getCharactersData(
        status: String,
        gender: String,
        name: String
    ): Flow<PagingData<ResultsModel>> {
        return withContext(Dispatchers.IO) {
            Log.d("Fetch Data", "RepositoryImpl")
            Pager(
                config = PagingConfig(pageSize = 25),
                pagingSourceFactory = {
                    CharactersPagingSource(
                        rickAndMortyApi,
                        statusState = status,
                        genderState = gender,
                        nameQuery = name
                    )
                }
            ).flow

        }
    }

}


