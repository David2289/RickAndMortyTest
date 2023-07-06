package com.davidpl.rickandmortytest.datasource.local

import com.davidpl.rickandmortytest.states.DataState
import com.davidpl.rickandmortytest.presenter.model.CharacterInfoModel
import com.davidpl.rickandmortytest.presenter.model.CharactersModel
import kotlinx.coroutines.flow.flow

class LocalCharactersDataSource {

    fun retrieveCharacters() = flow {
        emit(
            DataState.Success(
                CharactersModel(
                    info = CharacterInfoModel(0, 0, "", ""),
                    results = listOf()
                )
            )
        )
    }

}