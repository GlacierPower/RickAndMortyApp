package com.glacierpower.feature.characterDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glacierpower.domain.RickAndMortyInteractor
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.feature.characterDetails.state.CharacterDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val rickAndMortyInteractor: RickAndMortyInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<Int>("idCharacter")?.let { id ->
            getCharacterById(id)
        }
    }

    private val _state = MutableStateFlow(CharacterDetailsState())
    val state: StateFlow<CharacterDetailsState> get() = _state

    private fun getCharacterById(id: Int) {
        viewModelScope.launch() {
            val data = rickAndMortyInteractor.getCharacterById(id)

            _state.value = _state.value.copy(
                character = data
            )

            val episodeList = _state.value.character!!.episode
            val episodes: MutableList<EpisodeModel> = mutableListOf()
            _state.value = _state.value.copy(isLoading = true)
            episodeList.forEach { episodeUrl ->
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
