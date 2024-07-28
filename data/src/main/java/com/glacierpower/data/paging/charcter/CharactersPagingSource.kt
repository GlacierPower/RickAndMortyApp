package com.glacierpower.data.paging.charcter

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.glacierpower.data.mappers.character.toModel
import com.glacierpower.data.remote.RickAndMortyService
import com.glacierpower.data.utils.Constants.STARTING_PAGE
import com.glacierpower.domain.model.ResultsModel
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(
    private val rickAndMortyService: RickAndMortyService,
    private val statusState: String,
    private val genderState: String,
    private val nameQuery: String
) : PagingSource<Int, ResultsModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsModel> {
        val pageNumber = params.key ?: STARTING_PAGE

        return try {
            val response =
                rickAndMortyService.getCharacter(
                    page = pageNumber,
                    status = statusState,
                    gender = genderState,
                    name = nameQuery
                )

            val data = response.results

            var nextPageNumber: Int? = null

            if (response.info.next != null) {
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data.map { it.toModel() },
                prevKey = if (pageNumber == STARTING_PAGE) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsModel>): Int? {
        return null
    }


}