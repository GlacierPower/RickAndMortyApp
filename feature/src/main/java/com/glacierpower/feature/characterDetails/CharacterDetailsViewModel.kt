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
        savedStateHandle.get<Int>("id")?.let { id ->
            getCharacterById(id)
        }
    }

    private val _state = MutableStateFlow(CharacterDetailsState())
    val state: StateFlow<CharacterDetailsState> get() = _state

    private fun getCharacterById(id: Int) {
        viewModelScope.launch {
            val data = rickAndMortyInteractor.getCharacterById(id)

            _state.value = _state.value.copy(
                character = data
            )

            rickAndMortyInteractor.getCharacterById(id)

            val episodeList = _state.value.character!!.episode

            val episodes: MutableList<EpisodeModel> = mutableListOf()
            _state.value = _state.value.copy(isLoadingEpisodeInfo = true)
            episodeList.forEach { episode ->
                val episode = async {
                    val id = (episode.split("/"))[5]
                    rickAndMortyInteractor.getEpisodeById(id.toInt())
                }
                episode.await()
            }
        }
    }

}