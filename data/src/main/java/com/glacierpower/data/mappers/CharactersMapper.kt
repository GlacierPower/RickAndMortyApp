package com.glacierpower.data.mappers

import com.glacierpower.data.remote.response.CharacterResponse
import com.glacierpower.data.remote.response.Info
import com.glacierpower.data.remote.response.Location
import com.glacierpower.data.remote.response.Origin
import com.glacierpower.data.remote.response.Results
import com.glacierpower.domain.model.CharacterModel
import com.glacierpower.domain.model.InfoModel
import com.glacierpower.domain.model.LocationModel
import com.glacierpower.domain.model.OriginModel
import com.glacierpower.domain.model.ResultsModel

fun CharacterResponse.toModel(): CharacterModel {
    return CharacterModel(
        info.toModel(),
        results.map { result ->
            result.toModel()
        }
    )
}

fun Info.toModel(): InfoModel {
    return InfoModel(
        count, pages, next, prev
    )
}

fun Results.toModel(): ResultsModel {
    return ResultsModel(
        id,
        name,
        status,
        species,
        type,
        origin.toModel(),
        location.toModel(),
        image, episode, url, created
    )
}

fun Origin.toModel(): OriginModel {
    return OriginModel(
        name, url
    )
}

fun Location.toModel(): LocationModel {
    return LocationModel(
        name, url
    )
}
