package com.glacierpower.data.paging.location

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.glacierpower.data.mappers.loaction.toModel
import com.glacierpower.data.remote.RickAndMortyService
import com.glacierpower.domain.model.LocationResultModel
import javax.inject.Inject

class LocationPagingSource @Inject constructor(
    private val rickAndMortyService: RickAndMortyService,
    private val name: String,
    private val type: String,
    private val dimension:String
) : PagingSource<Int, LocationResultModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationResultModel> {
        val pageNumber = params.key ?: 1

        return try {
            val response =
                rickAndMortyService.getAllLocation(pageNumber, name, type,dimension)

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

    override fun getRefreshKey(state: PagingState<Int, LocationResultModel>): Int? {
        return null
    }


}