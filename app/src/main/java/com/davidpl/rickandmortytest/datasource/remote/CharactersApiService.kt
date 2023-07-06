package com.davidpl.rickandmortytest.datasource.remote

import com.davidpl.rickandmortytest.datasource.model.CharacterApiModel
import retrofit2.Response
import retrofit2.http.GET

interface CharactersApiService {

    @GET("api/character")
    suspend fun retrieveCharacters(): Response<CharacterApiModel>

}