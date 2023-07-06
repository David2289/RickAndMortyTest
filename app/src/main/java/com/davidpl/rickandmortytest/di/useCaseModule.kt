package com.davidpl.rickandmortytest.di

import com.davidpl.rickandmortytest.domain.RetrieveCharactersUseCase
import com.davidpl.rickandmortytest.domain.CharactersUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val TARGETS_USE_CASE = "TargetsUseCase"
internal const val RETRIEVE_RANKED_TARGETS_USE_CASE = "RetrieveRankedTargetsUseCase"

var useCaseModule = module {

    single(named(RETRIEVE_RANKED_TARGETS_USE_CASE)) {
        RetrieveCharactersUseCase(
            get(named(TARGETS_REPOSITORY))
        )
    }
    single(named(TARGETS_USE_CASE)) {
        CharactersUseCase(
            get(named(RETRIEVE_RANKED_TARGETS_USE_CASE))
        )
    }

}