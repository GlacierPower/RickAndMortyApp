package com.glacierpower.data.local.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.glacierpower.data.local.entity.CharacterEntity
import com.glacierpower.data.local.entity.EpisodeEntity
import com.glacierpower.data.local.entity.LocationEntity

@Database(
    entities = [CharacterEntity::class,LocationEntity::class,EpisodeEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class RickAndMortyDataBase():RoomDatabase() {

    abstract fun getDao() : RickAndMortyDao

    companion object{

        private const val DATA_BASE_NAME = "rickAndMortyDataBase"
        private var DB_INSTANCE: RickAndMortyDataBase? = null

        fun getDataBaseInstance(context: Context):RickAndMortyDataBase{
            return DB_INSTANCE?: Room
                .databaseBuilder(
                    context.applicationContext,
                    RickAndMortyDataBase::class.java,
                    DATA_BASE_NAME
                )
                .addMigrations()
                .build()
                .also { DB_INSTANCE = it }


        }

    }
}