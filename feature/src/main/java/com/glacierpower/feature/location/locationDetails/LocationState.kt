package com.glacierpower.feature.location.locationDetails

import com.glacierpower.domain.model.LocationResultModel
import com.glacierpower.domain.model.ResultsModel

data class LocationState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val locationDetails: LocationResultModel? = null,
    val character: List<ResultsModel>? = null
)
