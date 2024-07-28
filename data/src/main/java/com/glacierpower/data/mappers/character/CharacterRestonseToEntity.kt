package com.glacierpower.data.mappers.character

import com.glacierpower.common.response.CharacterData
import com.glacierpower.data.local.entity.CharacterEntity

fun CharacterData.toEntity():CharacterEntity{
    return CharacterEntity(
        id,
        name,
        status,
        species,
        type,
        gender,
        origin,
        location,
        image,
        episode,
        url,
        created
    )
}