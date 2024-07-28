package com.glacierpower.feature.character.character

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.glacierpower.domain.local.RickAndMortyLocalInteractor
import com.glacierpower.domain.model.ResultsModel
import com.glacierpower.domain.remote.RickAndMortyInteractor
import com.glacierpower.feature.character.character.state.CharacterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import util.ConnectivityManagerRepository
import javax.inject.Inject


@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val rickAndMortyInteractor: RickAndMortyInteractor,
    private val rickAndMortyLocalInteractor: RickAndMortyLocalInteractor,
    savedStateHandle: SavedStateHandle,
    connectivityManagerRepository: ConnectivityManagerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterState())
    val state: StateFlow<CharacterState> get() = _state

    private val name = savedStateHandle.get<String>("name").toString()
    private val gender = savedStateHandle.get<String>("gender").toString()
    private val status = savedStateHandle.get<String>("status").toString()

    private val isOnline = connectivityManagerRepository.isConnected

    init {
        insertCharacterData()
        viewModelScope.launch {
            isOnline.collectLatest { online ->
                if (online) {
                    getCharacters(status, gender, name).collectLatest {
                        _state.value = _state.value.copy(
                            characters = it
                        )

                    }
                } else {
                    getCharactersFromDb(status, gender, name).collectLatest {
                        _state.value = _state.value.copy(
                            characters = it
                        )
                    }

                }

            }
        }

    }

    private suspend fun getCharactersFromDb(
        status: String?,
        gender: String?,
        name: String?
    ): Flow<PagingData<ResultsModel>> {
        Log.i("Fetch Data", "ViewModel")
        return rickAndMortyLocalInteractor.getAllCharactersFromDb(
            status = status,
            gender = gender,
            name,
        ).cachedIn(viewModelScope)

    }


    private fun insertCharacterData() {
        viewModelScope.launch {
            rickAndMortyLocalInteractor.insertCharacterData()
        }
    }

    private suspend fun getCharacters(
        status: String,
        gender: String,
        name: String
    ): Flow<PagingData<ResultsModel>> {
        Log.i("Fetch Data", "ViewModel")
        return rickAndMortyInteractor.getCharactersData(
            status = status,
            gender = gender,
            name,
        ).cachedIn(viewModelScope)


    }

}
