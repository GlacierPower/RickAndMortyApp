package com.glacierpower.common.response

data class EpisodeResponse(
    val info: Info,
    val results: List<EpisodeResult>
)

data class EpisodeResult(
    val id: Int,
    val name: String,
    val air_date: String,
    val characters: List<String>,
    val url: String,
    val episode: String,
    val created: String
)
