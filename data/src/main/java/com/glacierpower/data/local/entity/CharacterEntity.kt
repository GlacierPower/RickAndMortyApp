package com.glacierpower.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characterEntity")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    val id:Int
)
