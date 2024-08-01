package com.glacierpower.feature.character.characterDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glacierpower.domain.local.RickAndMortyLocalInteractor
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.domain.remote.RickAndMortyInteractor
import com.glacierpower.feature.character.characterDetails.state.CharacterDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import util.ConnectivityManagerRepository
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val rickAndMortyInteractor: RickAndMortyInteractor,
    private val rickAndMortyLocalInteractor: RickAndMortyLocalInteractor,
    savedStateHandle: SavedStateHandle,
    connectivityManagerRepository: ConnectivityManagerRepository
) : ViewModel() {

    private val isOnline = connectivityManagerRepository.isConnected
    private val _state = MutableStateFlow(CharacterDetailsState())
    val state: StateFlow<CharacterDetailsState> get() = _state

    init {
        viewModelScope.launch {
            isOnline.collectLatest { online ->
                if (online) {
                    savedStateHandle.get<Int>("idCharacter")?.let { id ->
                        getCharacterById(id)
                    }
                } else {
                    savedStateHandle.get<Int>("idCharacter")?.let { id ->
                        getCharacterFromDbById(id)
                    }
                }
            }

        }
    }


    private fun getCharacterFromDbById(id: Int) {
        viewModelScope.launch() {
            val data = rickAndMortyLocalInteractor.getCharacterFromDbById(id)

            _state.value = _state.value.copy(
                character = data
            )

            val episodeList = _state.value.character!!.episode
            val episodes: MutableList<EpisodeModel> = mutableListOf()
            _state.value = _state.value.copy(isLoading = true)
            episodeList?.forEach { episodeUrl ->
                val episodeModel = async {
                    val episodeId = (episodeUrl.split("/"))[5]
                    val episode = rickAndMortyLocalInteractor.getEpisodeFromDbById(episodeId.toInt())
                    episode
                }
                episodes.add(episodeModel.await())

            }
            _state.value = _state.value.copy(
                episodeList = episodes,
                isLoading = false
            )
        }
    }

    private suspend fun getCharacterById(id: Int) {
        viewModelScope.launch() {
            val data = rickAndMortyInteractor.getCharacterById(id)

            _state.value = _state.value.copy(
                character = data
            )

            val episodeList = _state.value.character!!.episode
            val episodes: MutableList<EpisodeModel> = mutableListOf()
            _state.value = _state.value.copy(isLoading = true)
            episodeList?.forEach { episodeUrl ->
                val episodeModel = async {
                    val episodeId = (episodeUrl.split("/"))[5]
                    val episode = rickAndMortyInteractor.getEpisodeById(episodeId.toInt())
                    episode
                }
                episodes.add(episodeModel.await())
            }

            _state.value = _state.value.copy(
                episodeList = episodes,
                isLoading = false
            )
        }
    }
}
