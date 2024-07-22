package com.glacierpower.data.mappers

import com.glacierpower.common.response.LocationResponse
import com.glacierpower.common.response.LocationResults
import com.glacierpower.domain.model.LocationResponseModel
import com.glacierpower.domain.model.LocationResultModel

fun LocationResponse.toModel(): LocationResponseModel {
    return LocationResponseModel(
        info.toModel(),
        results.map { it.toModel() }
    )
}

fun LocationResults.toModel(): LocationResultModel {
    return LocationResultModel(
        id, name, type, dimension, residents, url, created
    )
}