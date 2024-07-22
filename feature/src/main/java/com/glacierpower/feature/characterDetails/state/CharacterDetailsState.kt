package com.glacierpower.feature.characterDetails.state

import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.domain.model.ResultsModel

data class CharacterDetailsState(
    val character: ResultsModel? = null,
    val navigateArgLocationId: Int? = null,
    val episodeList: List<EpisodeModel>? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)