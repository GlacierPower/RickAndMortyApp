package com.glacierpower.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.glacierpower.common.response.Location
import com.glacierpower.common.response.Origin

@Entity(tableName = "characterEntity")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
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
