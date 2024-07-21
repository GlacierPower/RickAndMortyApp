package com.glacierpower.data.repositoryImpl

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.glacierpower.data.local.dao.RickAndMortyDao
import com.glacierpower.data.mappers.toModel
import com.glacierpower.data.paging.CharactersPagingSource
import com.glacierpower.data.paging.EpisodeDataSource
import com.glacierpower.data.remote.RickAndMortyService
import com.glacierpower.domain.RickAndMortyRepository
import com.glacierpower.domain.model.EpisodeModel
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

    override suspend fun getCharacterById(id: Int): ResultsModel {
        return withContext(Dispatchers.IO) {
            rickAndMortyApi.getCharacterById(id).toModel()
        }
    }

    override suspend fun getEpisodeById(id: Int): EpisodeModel {
        return withContext(Dispatchers.IO) {
            rickAndMortyApi.getEpisodeById(id).toModel()
        }
    }

    override suspend fun getAllEpisode(
        name: String,
        episode: String
    ): Flow<PagingData<EpisodeModel>> {
        return withContext(Dispatchers.IO) {
            Pager(
                config = PagingConfig(pageSize = 25),
                pagingSourceFactory = {
                    EpisodeDataSource(rickAndMortyApi, name, episode)
                }
            ).flow
        }
    }
}




