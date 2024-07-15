package com.glacierpower.rickandmorty.presentation.characters

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.glacierpower.common.GenderState
import com.glacierpower.common.StatusState
import com.glacierpower.domain.model.ResultsModel

data class CharacterState(
    val characters: PagingData<ResultsModel>? = PagingData.empty(),
    val characterIdFromCharacterListFragment: Int = 1,
    val statusState: StatusState = StatusState.NONE,
    val genderState: GenderState = GenderState.NONE,
    val queryCharacterName: MutableLiveData<String> = MutableLiveData(""),
    val page: Int = 1,
    val isFilter: Boolean = false,
)