package com.davidpl.rickandmortytest.di

import com.davidpl.rickandmortytest.repository.CharactersRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val TARGETS_REPOSITORY = "TargetsRepository"

var repositoryModule = module {

    single(named(TARGETS_REPOSITORY)) {
        CharactersRepository(
            get(named(LOCAL_TARGETS_DATA_SOURCE)),
            get(named(REMOTE_TARGETS_DATA_SOURCE))
        )
    }

}