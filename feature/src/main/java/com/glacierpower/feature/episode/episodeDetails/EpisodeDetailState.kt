package com.glacierpower.feature.episode.episodeDetails

import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.domain.model.ResultsModel

data class EpisodeDetailState(
    val isLoading: Boolean = false,
    val character: List<ResultsModel>? = null,
    val episodeDetail: EpisodeModel? = null,
    val error: String = ""
)