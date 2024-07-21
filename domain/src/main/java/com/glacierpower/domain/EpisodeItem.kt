package com.glacierpower.domain

import com.glacierpower.domain.model.EpisodeModel

sealed class EpisodeItem() {
    data class Item(val episode: EpisodeModel) : EpisodeItem()

    data class SeparatorItem(val season: String) : EpisodeItem()
}

val EpisodeItem.Item.season: Int
    get() = this.episode.episode.substring(2, 3).toInt()