package com.glacierpower.rickandmorty.presentation.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.glacierpower.domain.RickAndMortyInteractor
import com.glacierpower.domain.model.ResultsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val rickAndMortyInteractor: RickAndMortyInteractor
) : ViewModel() {

   suspend fun getCharacters(
        status: String,
        gender: String,
        name: String
    ): Flow<PagingData<ResultsModel>> {
        Log.i("Fetch Data", "ViewModel")
        return rickAndMortyInteractor.getCharactersData(
            status = status,
            gender = gender,
            name,
        )

    }

}
