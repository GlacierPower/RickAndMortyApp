package com.glacierpower.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodeEntity")
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)
