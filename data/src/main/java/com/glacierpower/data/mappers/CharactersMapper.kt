package com.glacierpower.data.mappers

import com.glacierpower.common.CharacterData
import com.glacierpower.common.CharacterResponse
import com.glacierpower.common.Info
import com.glacierpower.common.Location
import com.glacierpower.common.Origin
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
        count, next, pages, prev
    )
}

fun CharacterData.toModel(): ResultsModel {
    return ResultsModel(
        id,
        name,
        status,
        species,
        gender,
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
