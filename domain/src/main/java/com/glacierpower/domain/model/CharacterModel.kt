package com.glacierpower.domain.model

data class CharacterModel(
    val info: InfoModel,
    val results: List<ResultsModel>
)

data class InfoModel(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?

    )

data class ResultsModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginModel,
    val location: LocationModel,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String

)

data class OriginModel(
    val name: String,
    val url: String
)

data class LocationModel(
    val name: String,
    val url: String
)

