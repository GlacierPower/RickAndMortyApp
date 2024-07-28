package com.glacierpower.data.mappers.character

import com.glacierpower.data.local.entity.CharacterEntity
import com.glacierpower.domain.model.ResultsModel

fun CharacterEntity.toModel(): ResultsModel {
    return ResultsModel(
        id,
        name,
        status,
        species,
        type,
        gender,
        origin.toModel(),
        location.toModel(),
        image,
        episode,
        url,
        created
    )
}