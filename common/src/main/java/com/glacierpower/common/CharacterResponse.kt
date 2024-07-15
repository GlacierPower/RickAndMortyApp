package com.glacierpower.common

import kotlinx.serialization.SerialName


data class CharacterResponse(
    @SerialName("info")
    val info: Info,
    val results: List<CharacterData>
)


data class Info(
    @SerialName("count")
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?

    )


data class CharacterData(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String

)


data class Origin(
    val name: String,
    val url: String
)

data class Location(
    val name: String,
    val url: String
)

