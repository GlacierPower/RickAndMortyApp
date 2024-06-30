package com.glacierpower.data.di

import android.content.Context
import com.glacierpower.data.local.dao.RickAndMortyDao
import com.glacierpower.data.local.dao.RickAndMortyDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun provideRickAndMortyDatabase(context: Context) =
        RickAndMortyDataBase.getDataBaseInstance(context)

    @Provides
    fun provideRickAndMortyDao(rickAndMortyDataBase: RickAndMortyDataBase):RickAndMortyDao{
        return rickAndMortyDataBase.getDao()
    }
}