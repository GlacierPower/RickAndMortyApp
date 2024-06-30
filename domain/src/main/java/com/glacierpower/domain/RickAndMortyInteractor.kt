package com.glacierpower.domain

import javax.inject.Inject

class RickAndMortyInteractor @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
){
}