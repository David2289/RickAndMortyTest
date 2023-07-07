package com.davidpl.rickandmortytest.domain

import com.davidpl.rickandmortytest.repository.CharactersRepository
import kotlinx.coroutines.flow.flow

class RetrieveCharactersUseCase(
    private val charactersRepository: CharactersRepository
) {

    operator fun invoke() = flow {
        emit(charactersRepository.retrieveCharacters())
    }

}