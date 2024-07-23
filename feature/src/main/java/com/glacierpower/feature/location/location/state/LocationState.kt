package com.glacierpower.feature.location.location.state

import androidx.paging.PagingData
import com.glacierpower.domain.model.LocationResultModel

data class LocationState(
    val locationList: PagingData<LocationResultModel>? = PagingData.empty()
)
