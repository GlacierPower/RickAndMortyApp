package com.glacierpower.domain.di

import com.glacierpower.domain.local.RickAndMortyLocalInteractor
import com.glacierpower.domain.local.RickAndMortyLocalRepository
import com.glacierpower.domain.remote.RickAndMortyInteractor
import com.glacierpower.domain.remote.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideRickAndMortyInteractor(
        rickAndMortyRepository: RickAndMortyRepository
    ) = RickAndMortyInteractor(rickAndMortyRepository)

    @Provides
    fun provideRickAndMortyLocalInteractor(
        rickAndMortyLocalRepository: RickAndMortyLocalRepository
    ) = RickAndMortyLocalInteractor(rickAndMortyLocalRepository)
}