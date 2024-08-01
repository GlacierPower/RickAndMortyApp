package com.glacierpower.feature.episode.episodeDetails

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
class EpisodeDetailsViewModel @Inject constructor(
    private val rickAndMortyInteractor: RickAndMortyInteractor,
    savedStateHandle: SavedStateHandle,
    private val rickAndMortyLocalInteractor: RickAndMortyLocalInteractor,
    connectivityManagerRepository: ConnectivityManagerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EpisodeDetailState())
    val state: StateFlow<EpisodeDetailState> get() = _state

    private val isOnline = connectivityManagerRepository.isConnected

    init {
        viewModelScope.launch {
            isOnline.collectLatest {online->
                if(online){
                    savedStateHandle.get<Int>("idEpisode")?.let { idEpisode ->
                        getEpisodeDetail(idEpisode)
                    }
                }else{
                    savedStateHandle.get<Int>("idEpisode")?.let { idEpisode ->
                        getEpisodeDetailFromDB(idEpisode)
                    }
                }

            }
        }
    }

    private fun getEpisodeDetailFromDB(id: Int) {

        if (_state.value.character == null) {
            try {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = ""
                )
                viewModelScope.launch {

                    val response = rickAndMortyLocalInteractor.getEpisodeFromDbById(id)

                    _state.value = _state.value.copy(episodeDetail = response)

                    val characterList = mutableListOf<ResultsModel>()
                    response.characters.forEach { characterUrl ->
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

    private fun getEpisodeDetail(id: Int) {

        if (_state.value.character == null) {
            try {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = ""
                )
                viewModelScope.launch {

                    val response = rickAndMortyInteractor.getEpisodeById(id)

                    _state.value = _state.value.copy(episodeDetail = response)

                    val characterList = mutableListOf<ResultsModel>()
                    response.characters.forEach { characterUrl ->
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