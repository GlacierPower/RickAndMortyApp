package com.glacierpower.common.response

data class LocationResponse(
    val info: Info,
    val results: List<LocationResults>
)

data class LocationResults(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)