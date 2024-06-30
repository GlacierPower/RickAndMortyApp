package com.glacierpower.data.remote

import com.glacierpower.data.remote.response.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("/character")
    suspend fun getCharacter(
        @Query("status") status :String="",
        @Query("gender") gender :String="",
        @Query("name") name:String="",
        @Query("page") page:Int? = null
    ): CharacterResponse
}