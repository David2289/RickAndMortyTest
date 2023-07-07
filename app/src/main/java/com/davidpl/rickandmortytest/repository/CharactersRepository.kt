package com.davidpl.rickandmortytest.repository

import com.davidpl.rickandmortytest.datasource.local.LocalCharactersDataSource
import com.davidpl.rickandmortytest.datasource.remote.RemoteCharactersDataSource
import com.davidpl.rickandmortytest.states.DataState
import com.davidpl.rickandmortytest.presenter.model.CharactersModel
import kotlinx.coroutines.flow.Flow

open class CharactersRepository(
    private val localCharactersDataSource: LocalCharactersDataSource,
    private val remoteCharactersDataSource: RemoteCharactersDataSource
) {

    suspend fun retrieveCharacters(fromRemote: Boolean = true): DataState<CharactersModel> {
        return when (fromRemote) {
            true -> {
                val result = remoteCharactersDataSource.retrieveCharacters()
                when (result) {
                    is DataState.Success -> {
                        localCharactersDataSource.insertCharacters(result.data.results)
                        result
                    }
                    else -> result
                }
            }
            else -> {
                localCharactersDataSource.retrieveCharacters()
            }
        }
    }

}