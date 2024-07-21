package com.glacierpower.data.remote

import com.glacierpower.common.CharacterData
import com.glacierpower.common.CharacterResponse
import com.glacierpower.common.EpisodeResponse
import com.glacierpower.common.EpisodeResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character/")
    suspend fun getCharacter(
        @Query("status") status: String = "",
        @Query("gender") gender: String = "",
        @Query("name") name: String = "",
        @Query("page") page: Int? = null
    ): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterData

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): EpisodeResult

    @GET("episode")
    suspend fun getAllEpisode(
        @Query("page") page: Int? = null,
        @Query("name") name: String = "",
        @Query("episode") episode: String = ""
    ): EpisodeResponse

}