package com.glacierpower.feature.character.character.state

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.glacierpower.domain.model.ResultsModel

data class CharacterState(
    val characters: PagingData<ResultsModel>? = PagingData.empty(),
    val characterIdFromCharacterListFragment: Int = 1,
    val queryCharacterName: MutableLiveData<String> = MutableLiveData(""),
    val page: Int = 1,
    val isFilter: Boolean = false,
)