package com.davidpl.rickandmortytest.datasource.remote

import com.davidpl.rickandmortytest.states.DataState
import com.davidpl.rickandmortytest.presenter.model.CharactersModel
import com.davidpl.rickandmortytest.datasource.model.toDomain

class RemoteCharactersDataSource(
    private val charactersAPIService: CharactersApiService
) {

    suspend fun retrieveCharacters(): DataState<CharactersModel> =
        handleResponse(
            call = { charactersAPIService.retrieveCharacters() },
            toDomain = { toDomain() }
        )

}