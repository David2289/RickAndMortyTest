package com.davidpl.rickandmortytest.domain

data class CharactersUseCase(
    val retrieveCharactersUseCase: RetrieveCharactersUseCase,
    val retrieveCharUseCase: RetrieveCharUseCase
)