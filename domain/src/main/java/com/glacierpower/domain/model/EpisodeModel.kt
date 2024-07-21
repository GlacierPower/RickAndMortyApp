package com.glacierpower.domain.model

data class EpisodeResponseModel(
    val info: InfoModel,
    val results: List<EpisodeModel>
)

data class EpisodeModel(
    val id: Int,
    val name: String,
    val air_date: String,
    val characters: List<String>,
    val url: String,
    val episode: String,
    val created: String
)