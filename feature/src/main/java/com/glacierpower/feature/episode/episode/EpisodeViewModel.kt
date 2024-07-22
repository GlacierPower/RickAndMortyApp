package com.glacierpower.feature.episode.episode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.glacierpower.domain.RickAndMortyInteractor
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.feature.episode.episode.state.EpisodeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val rickAndMortyInteractor: RickAndMortyInteractor,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val name = savedStateHandle.get<String>("name").toString()
    private val episode = savedStateHandle.get<String>("episode").toString()

    private val _state = MutableStateFlow(EpisodeState())
    val state: StateFlow<EpisodeState> get() = _state

    init {
        viewModelScope.launch {
            getAllEpisode(name, episode).collect {
                _state.value = _state.value.copy(
                    episodeList = it
                )
            }
        }
    }

    private suspend fun getAllEpisode(
        name: String,
        episode: String
    ): Flow<PagingData<EpisodeModel>> {
        return rickAndMortyInteractor.getAllEpisode(name, episode).cachedIn(viewModelScope)
    }
}