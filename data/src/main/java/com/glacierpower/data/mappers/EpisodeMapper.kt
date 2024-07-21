package com.glacierpower.data.mappers

import com.glacierpower.common.EpisodeResponse
import com.glacierpower.common.EpisodeResult
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.domain.model.EpisodeResponseModel

fun EpisodeResponse.toModel(): EpisodeResponseModel {
    return EpisodeResponseModel(
        info.toModel(),
        results.map {
            it.toModel()
        }
    )
}

fun EpisodeResult.toModel(): EpisodeModel {
    return EpisodeModel(
        id, name, air_date, characters, url, episode, created
    )
}