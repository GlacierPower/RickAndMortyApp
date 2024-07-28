package com.glacierpower.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locationEntity")
data class LocationEntity(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)

