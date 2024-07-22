package com.glacierpower.feature.episode.episode.state

import androidx.paging.PagingData
import com.glacierpower.domain.model.EpisodeModel

data class EpisodeState(
    val episodeList: PagingData<EpisodeModel>? = PagingData.empty()
)
