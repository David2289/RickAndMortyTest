package com.davidpl.rickandmortytest.datasource.remote

import com.davidpl.rickandmortytest.states.DataState
import com.davidpl.rickandmortytest.presenter.model.CharactersModel
import com.davidpl.rickandmortytest.datasource.model.toDomain
import kotlinx.coroutines.flow.Flow

class RemoteCharactersDataSource(
    private val charactersAPIService: CharactersApiService
) {

    fun retrieveCharacters(): Flow<DataState<CharactersModel>> =
        handleResponse(
            call = { charactersAPIService.retrieveCharacters() },
            toDomain = { toDomain() }
        )

}