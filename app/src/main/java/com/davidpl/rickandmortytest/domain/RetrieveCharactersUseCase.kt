package com.davidpl.rickandmortytest.domain

import com.davidpl.rickandmortytest.repository.CharactersRepository

class RetrieveCharactersUseCase(
    private val charactersRepository: CharactersRepository
) {

    operator fun invoke() = charactersRepository.retrieveCharacters()

}