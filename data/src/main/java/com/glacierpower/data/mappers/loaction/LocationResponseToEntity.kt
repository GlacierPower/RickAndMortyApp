package com.glacierpower.data.mappers.loaction

import com.glacierpower.common.response.LocationResults
import com.glacierpower.data.local.entity.LocationEntity

fun LocationResults.toEntity(): LocationEntity {
    return LocationEntity(
        id,
        name,
        type,
        dimension,
        residents,
        url,
        created
    )
}