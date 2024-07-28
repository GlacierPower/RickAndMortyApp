package com.glacierpower.data.mappers.episode

import com.glacierpower.data.local.entity.EpisodeEntity
import com.glacierpower.domain.model.EpisodeModel

fun EpisodeEntity.toModel(): EpisodeModel {
    return EpisodeModel(
        id, name, air_date, episode, characters, url, created
    )
}