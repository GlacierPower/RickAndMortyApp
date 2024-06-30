package com.glacierpower.data.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.glacierpower.data.mappers.toModel
import com.glacierpower.data.remote.RickAndMortyService
import com.glacierpower.data.utils.GenderState
import com.glacierpower.data.utils.StatusState
import com.glacierpower.domain.model.ResultsModel
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

class CharacterPagingSource @Inject constructor(
    private val rickAndMortyService: RickAndMortyService,
    private val statusState: StatusState,
    private val genderState: GenderState,
    private val name:String
    ):PagingSource<Int, ResultsModel>() {
    override fun getRefreshKey(state: PagingState<Int, ResultsModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsModel> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response =
                rickAndMortyService.getCharacter(
                    page = pageNumber,
                    status = statusState.name,
                    gender = genderState.name,
                    name = name
                )

            val data = response.results

            var nextPageNumber: Int? = null

            val uri = Uri.parse(response.info.next)
            val nextPageQuery = uri.getQueryParameter("page")
            nextPageNumber = nextPageQuery?.toInt()

            LoadResult.Page(
                data = data.map { it.toModel() },
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
