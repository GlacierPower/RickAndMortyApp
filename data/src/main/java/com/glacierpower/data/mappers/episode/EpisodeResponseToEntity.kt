package com.glacierpower.data.mappers.episode

import com.glacierpower.common.response.EpisodeResult
import com.glacierpower.data.local.entity.EpisodeEntity

fun EpisodeResult.toEntity(): EpisodeEntity {
    return EpisodeEntity(
        id, name, air_date, episode, characters, url, created
    )
}
