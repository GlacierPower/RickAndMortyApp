package com.glacierpower.data.paging.charcter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.glacierpower.data.local.dao.RickAndMortyDao
import com.glacierpower.data.mappers.character.toModel
import com.glacierpower.data.utils.Constants.STARTING_PAGE
import com.glacierpower.domain.model.ResultsModel
import javax.inject.Inject

class CharacterPagingSourceDB @Inject constructor(
    private val rickAndMortyDao: RickAndMortyDao,
    private val name:String? = null,
    private val gender:String? = null,
    private val status:String? = null
) : PagingSource<Int, ResultsModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsModel> {
        val pageNumber = params.key ?: STARTING_PAGE

        return try {
            val response = rickAndMortyDao.getAllCharacters(name = name, gender = gender, status = status)
            LoadResult.Page(
                data = response.map { it.toModel() },
                prevKey = if (pageNumber == STARTING_PAGE) null else pageNumber - 1,
                nextKey = null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsModel>): Int? {
        return null
    }


}
