package com.glacierpower.data.mappers.episode

import com.glacierpower.common.response.EpisodeResponse
import com.glacierpower.common.response.EpisodeResult
import com.glacierpower.data.mappers.character.toModel
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