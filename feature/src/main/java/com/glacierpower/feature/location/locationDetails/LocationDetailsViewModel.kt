package com.glacierpower.feature.location.locationDetails

import android.net.http.HttpException
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glacierpower.domain.local.RickAndMortyLocalInteractor
import com.glacierpower.domain.model.ResultsModel
import com.glacierpower.domain.remote.RickAndMortyInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import util.ConnectivityManagerRepository
import javax.inject.Inject

@HiltViewModel
class LocationDetailsViewModel @Inject constructor(
    private val rickAndMortyInteractor: RickAndMortyInteractor,
    savedStateHandle: SavedStateHandle,
    private val rickAndMortyLocalInteractor: RickAndMortyLocalInteractor,
    connectivityManagerRepository: ConnectivityManagerRepository
) : ViewModel() {

    private var _state = MutableStateFlow(LocationState())
    val state: StateFlow<LocationState> get() = _state

    private val isOnline = connectivityManagerRepository.isConnected

    init {
        viewModelScope.launch {
            isOnline.collectLatest { online ->
                if (online) {
                    savedStateHandle.get<Int>("idLocation")?.let { idLocation ->
                        getLocationById(idLocation)
                    }
                } else {
                    savedStateHandle.get<Int>("idLocation")?.let { idLocation ->
                        getLocationFromDBById(idLocation)
                    }
                }

            }

        }
    }

    private suspend fun getLocationFromDBById(id: Int) {

        if (_state.value.character == null) {
            try {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = ""
                )
                viewModelScope.launch {

                    val response = rickAndMortyLocalInteractor.getLocationFromDbById(id)

                    _state.value = _state.value.copy(locationDetails = response)

                    val characterList = mutableListOf<ResultsModel>()
                    response.residents.forEach { characterUrl ->
                        val characterDeferred = async {
                            val id = (characterUrl.split("/"))[5].toInt()
                            val character =
                                rickAndMortyLocalInteractor.getCharacterFromDbById(id)
                            character
                        }
                        characterList.add(characterDeferred.await())
                    }

                    _state.value = _state.value.copy(
                        character = characterList
                    )
                    _state.value = _state.value.copy(
                        isLoading = false
                    )


                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message.toString())
            } catch (e: HttpException) {
                _state.value = _state.value.copy(
                    error = e.message.toString()
                )
            }
        }
    }

    private suspend fun getLocationById(id: Int) {

        if (_state.value.character == null) {
            try {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = ""
                )
                viewModelScope.launch {

                    val response = rickAndMortyInteractor.getLocationById(id)

                    _state.value = _state.value.copy(locationDetails = response)

                    val characterList = mutableListOf<ResultsModel>()
                    response.residents.forEach { characterUrl ->
                        val characterDeferred = async {
                            val id = (characterUrl.split("/"))[5].toInt()
                            val character =
                                rickAndMortyInteractor.getCharacterById(id)
                            character
                        }
                        characterList.add(characterDeferred.await())
                    }

                    _state.value = _state.value.copy(
                        character = characterList
                    )
                    _state.value = _state.value.copy(
                        isLoading = false
                    )


                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message.toString())
            } catch (e: HttpException) {
                _state.value = _state.value.copy(
                    error = e.message.toString()
                )
            }
        }
    }
}