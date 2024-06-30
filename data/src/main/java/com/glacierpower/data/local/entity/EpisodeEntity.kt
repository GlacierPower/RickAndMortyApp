package com.glacierpower.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodeEntity")
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int
)
