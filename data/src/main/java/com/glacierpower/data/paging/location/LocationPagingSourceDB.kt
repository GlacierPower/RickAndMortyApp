package com.glacierpower.data.paging.location

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.glacierpower.data.local.dao.RickAndMortyDao
import com.glacierpower.data.mappers.loaction.toModel
import com.glacierpower.data.utils.Constants
import com.glacierpower.domain.model.LocationResultModel
import javax.inject.Inject

class LocationPagingSourceDB @Inject constructor(
    private val rickAndMortyDao: RickAndMortyDao,
    private val type: String? = null,
    private val dimension: String? = null,
    private val name: String? = null
) : PagingSource<Int, LocationResultModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationResultModel> {
        val pageNumber = params.key ?: Constants.STARTING_PAGE

        return try {
            val response =
                rickAndMortyDao.getAllLocation(type = type, dimension = dimension, name = name)
            LoadResult.Page(
                data = response.map { it.toModel() },
                prevKey = if (pageNumber == Constants.STARTING_PAGE) null else pageNumber - 1,
                nextKey = null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LocationResultModel>): Int? {
        return null
    }


}