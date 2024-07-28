package com.glacierpower.feature.episode.episodeDetails

import android.net.http.HttpException
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glacierpower.domain.remote.RickAndMortyInteractor
import com.glacierpower.domain.model.ResultsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor(
    private val rickAndMortyInteractor: RickAndMortyInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(EpisodeDetailState())
    val state: StateFlow<EpisodeDetailState> get() = _state

    init {
        savedStateHandle.get<Int>("idEpisode")?.let {idEpisode->
            getEpisodeDetail(idEpisode)
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