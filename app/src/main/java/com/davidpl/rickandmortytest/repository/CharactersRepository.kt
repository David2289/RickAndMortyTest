package com.davidpl.rickandmortytest.repository

import com.davidpl.rickandmortytest.datasource.local.LocalCharactersDataSource
import com.davidpl.rickandmortytest.datasource.remote.RemoteCharactersDataSource
import com.davidpl.rickandmortytest.states.DataState
import com.davidpl.rickandmortytest.presenter.model.CharactersModel
import kotlinx.coroutines.flow.Flow

class CharactersRepository(
    private val localCharactersDataSource: LocalCharactersDataSource,
    private val remoteCharactersDataSource: RemoteCharactersDataSource
) {

    fun retrieveCharacters(fromRemote: Boolean = true): Flow<DataState<CharactersModel>> {
        return when (fromRemote) {
            true -> {
                remoteCharactersDataSource.retrieveCharacters()
            }
            else -> {
                localCharactersDataSource.retrieveCharacters()
            }
        }
    }

}