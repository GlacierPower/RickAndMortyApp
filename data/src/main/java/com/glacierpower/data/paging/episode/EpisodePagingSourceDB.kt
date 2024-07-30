package com.glacierpower.data.paging.episode

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.glacierpower.data.local.dao.RickAndMortyDao
import com.glacierpower.data.mappers.episode.toModel
import com.glacierpower.data.utils.Constants
import com.glacierpower.domain.model.EpisodeModel
import javax.inject.Inject

class EpisodePagingSourceDB @Inject constructor(
    private val rickAndMortyDao: RickAndMortyDao,
    private val name:String? = null,
    private val episode:String? = null,
) : PagingSource<Int, EpisodeModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeModel> {
        val pageNumber = params.key ?: Constants.STARTING_PAGE

        return try {
            val response = rickAndMortyDao.getAllEpisode(name = name, episode = episode)
            LoadResult.Page(
                data = response.map { it.toModel() },
                prevKey = if (pageNumber == Constants.STARTING_PAGE) null else pageNumber - 1,
                nextKey = null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeModel>): Int? {
        return null
    }


}