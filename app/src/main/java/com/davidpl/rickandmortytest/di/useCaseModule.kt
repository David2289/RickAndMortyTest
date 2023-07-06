package com.davidpl.rickandmortytest.di

import com.davidpl.rickandmortytest.domain.RetrieveCharactersUseCase
import com.davidpl.rickandmortytest.domain.CharactersUseCase
import com.davidpl.rickandmortytest.domain.RetrieveCharUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val CHARACTERS_USE_CASE = "CharactersUseCase"
internal const val RETRIEVE_CHARACTERS_USE_CASE = "RetrieveCharactersUseCase"
internal const val RETRIEVE_CHAR_USE_CASE = "RetrieveCharUseCase"

var useCaseModule = module {

    single(named(RETRIEVE_CHARACTERS_USE_CASE)) {
        RetrieveCharactersUseCase(
            get(named(TARGETS_REPOSITORY))
        )
    }
    single(named(RETRIEVE_CHAR_USE_CASE)) {
        RetrieveCharUseCase(
            get(named(TARGETS_REPOSITORY))
        )
    }
    single(named(CHARACTERS_USE_CASE)) {
        CharactersUseCase(
            get(named(RETRIEVE_CHARACTERS_USE_CASE)),
            get(named(RETRIEVE_CHAR_USE_CASE))
        )
    }

}