package com.davidpl.rickandmortytest.domain

import com.davidpl.rickandmortytest.presenter.model.CharacterResultModel
import com.davidpl.rickandmortytest.repository.CharactersRepository
import com.davidpl.rickandmortytest.states.DataState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class RetrieveCharUseCase(
    private val charactersRepository: CharactersRepository
) {

    operator fun invoke(characterId: Int) = flow<DataState<CharacterResultModel>> {
        charactersRepository
            .retrieveCharacters()
            .onEach { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val character = dataState.data.results.find { it.id == characterId }
                        character?.let {
                            emit(DataState.Success(character))
                        } ?: emit(DataState.ErrorExpected("Character Not found"))
                    }
                    is DataState.ErrorUnexpected -> { emit(dataState) }
                    is DataState.Failure -> { emit(dataState) }
                    is DataState.NoAuth -> { emit(dataState) }
                    else -> {
                        DataState.ErrorExpected("Something went wrong")
                    }
                }
            }
            .collect()
    }

}