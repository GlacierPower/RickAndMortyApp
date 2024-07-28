package com.glacierpower.feature.location.location

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.glacierpower.domain.local.RickAndMortyLocalInteractor
import com.glacierpower.domain.model.LocationResultModel
import com.glacierpower.domain.remote.RickAndMortyInteractor
import com.glacierpower.feature.location.location.state.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import util.ConnectivityManagerRepository
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val rickAndMortyInteractor: RickAndMortyInteractor,
    private val rickAndMortyLocalInteractor: RickAndMortyLocalInteractor,
    connectivityManagerRepository: ConnectivityManagerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val locationName = savedStateHandle.get<String>("locationName").toString()
    private val locationType = savedStateHandle.get<String>("locationType").toString()
    private val locationDimension = savedStateHandle.get<String>("locationDimension").toString()

    private var _state = MutableStateFlow(LocationState())
    val state: StateFlow<LocationState> get() = _state

    private val isOnline = connectivityManagerRepository.isConnected

    init {
        viewModelScope.launch {
            insertLocationData()
            isOnline.collectLatest { online ->
                if (online) {
                    getAllLocation(locationName, locationType, locationDimension).collect {
                        _state.value = _state.value.copy(
                            locationList = it
                        )
                    }

                } else {
                    getLocationFromDB(locationName, locationType, locationDimension).collect {
                        _state.value = _state.value.copy(
                            locationList = it
                        )
                    }
                }
            }

        }
    }

    private suspend fun insertLocationData() {
        rickAndMortyLocalInteractor.insertLocationData()
    }

    private suspend fun getLocationFromDB(
        name: String,
        type: String,
        dimension: String
    ): Flow<PagingData<LocationResultModel>> {
        return rickAndMortyLocalInteractor.getAllLocationFromDb(name, type, dimension).cachedIn(
            viewModelScope)
    }

    private suspend fun getAllLocation(
        name: String,
        type: String,
        dimension: String
    ): Flow<PagingData<LocationResultModel>> {
        Log.i("Get location", "ViewModel")
        return rickAndMortyInteractor.getAllLocation(name, type, dimension).cachedIn(viewModelScope)
    }
}