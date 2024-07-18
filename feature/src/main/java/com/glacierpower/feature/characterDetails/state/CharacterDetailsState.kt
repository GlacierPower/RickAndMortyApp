package com.glacierpower.feature.characterDetails.state

import com.glacierpower.domain.model.ResultsModel

data class CharacterDetailsState(
    val character: ResultsModel? = null,
    val navigateArgLocationId: Int? = null,
    val isLoadingEpisodeInfo: Boolean = false
)