package com.glacierpower.domain.di

import com.glacierpower.domain.RickAndMortyInteractor
import com.glacierpower.domain.RickAndMortyRepository
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
    )= RickAndMortyInteractor(rickAndMortyRepository)
}