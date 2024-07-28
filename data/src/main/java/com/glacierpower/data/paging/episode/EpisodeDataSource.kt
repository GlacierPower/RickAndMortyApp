package com.glacierpower.data.paging.episode

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.glacierpower.data.mappers.episode.toModel
import com.glacierpower.data.remote.RickAndMortyService
import com.glacierpower.domain.model.EpisodeModel
import javax.inject.Inject


class EpisodeDataSource @Inject constructor(
    private val rickAndMortyService: RickAndMortyService,
    private val name: String,
    private val episode: String
) : PagingSource<Int, EpisodeModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeModel> {
        val pageNumber = params.key ?: 1

        return try {
            val response =
                rickAndMortyService.getAllEpisode(pageNumber, name, episode)

            val data = response.results

            var nextPageNumber: Int? = null

            if (response.info.next != null) {
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data.map { it.toModel() },
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeModel>): Int? {
        return null
    }


}