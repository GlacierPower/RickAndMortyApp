package com.glacierpower.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locationEntity")
data class LocationEntity(
    @PrimaryKey(autoGenerate = false)
    val id:Int
)
