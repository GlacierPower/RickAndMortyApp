package com.glacierpower.feature.episode.episode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.glacierpower.domain.local.RickAndMortyLocalInteractor
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.domain.remote.RickAndMortyInteractor
import com.glacierpower.feature.episode.episode.state.EpisodeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import util.ConnectivityManagerRepository
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val rickAndMortyInteractor: RickAndMortyInteractor,
    savedStateHandle: SavedStateHandle,
    private val rickAndMortyLocalInteractor: RickAndMortyLocalInteractor,
    connectivityManagerRepository: ConnectivityManagerRepository
) :
    ViewModel() {

    private val name = savedStateHandle.get<String>("name").toString()
    private val episode = savedStateHandle.get<String>("episode").toString()

    private val nameDB = savedStateHandle.get<String>("nameDB")
    private val episodeDB = savedStateHandle.get<String>("episodeDB")

    private val _state = MutableStateFlow(EpisodeState())
    val state: StateFlow<EpisodeState> get() = _state

    private val isOnline = connectivityManagerRepository.isConnected

    init {
        viewModelScope.launch {
            insertEpisodeData()
            isOnline.collectLatest { online ->
                if (online) {
                    getAllEpisode(name, episode).collect {
                        _state.value = _state.value.copy(
                            episodeList = it
                        )
                    }
                }
                getAllEpisodeFromDB().collectLatest {
                    _state.value = _state.value.copy(
                        episodeList = it
                    )
                }

            }

        }
    }

    private suspend fun insertEpisodeData() {
        rickAndMortyLocalInteractor.insertEpisodeData()
    }

    private suspend fun getAllEpisodeFromDB(
    ): Flow<PagingData<EpisodeModel>> {
        return rickAndMortyLocalInteractor.getAllEpisodeFromDb(name = nameDB, episode = episodeDB)
            .cachedIn(viewModelScope)
    }

    private suspend fun getAllEpisode(
        name: String,
        episode: String
    ): Flow<PagingData<EpisodeModel>> {
        return rickAndMortyInteractor.getAllEpisode(name, episode).cachedIn(viewModelScope)
    }
}