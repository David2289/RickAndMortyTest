package com.davidpl.rickandmortytest.domain

import com.davidpl.rickandmortytest.repository.CharactersRepository
import com.davidpl.rickandmortytest.states.DataState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class RetrieveCharUseCase(
    private val charactersRepository: CharactersRepository
) {

    operator fun invoke(characterId: Int) = flow {
        val result = charactersRepository.retrieveCharacters(fromRemote = false)
        when (result) {
            is DataState.Success -> {
                val character = result.data.results.find { it.id == characterId }
                character?.let {
                    emit(DataState.Success(character))
                } ?: emit(DataState.ErrorExpected("Character Not found"))
            }
            is DataState.ErrorUnexpected -> { emit(result) }
            is DataState.Failure -> { emit(result) }
            is DataState.NoAuth -> { emit(result) }
            else -> {
                DataState.ErrorExpected("Something went wrong")
            }
        }
    }

}