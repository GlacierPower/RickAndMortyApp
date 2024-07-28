package com.glacierpower.data.mappers.loaction

import com.glacierpower.data.local.entity.LocationEntity
import com.glacierpower.domain.model.LocationResultModel

fun LocationEntity.toModel(): LocationResultModel {
    return LocationResultModel(
        id, name, type, dimension, residents, url, created
    )

}