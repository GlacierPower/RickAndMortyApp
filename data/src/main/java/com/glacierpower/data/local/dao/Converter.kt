package com.glacierpower.data.local.dao

import androidx.room.TypeConverter
import com.glacierpower.common.response.Location
import com.glacierpower.common.response.Origin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromOrigin(origin: Origin): String {
        return Gson().toJson(origin)
    }

    @TypeConverter
    fun toOrigin(value: String): Origin {
        return Gson().fromJson(value, Origin::class.java)
    }

    @TypeConverter
    fun fromLocation(location: Location): String {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun toLocation(value: String): Location {
        return Gson().fromJson(value, Location::class.java)
    }
}