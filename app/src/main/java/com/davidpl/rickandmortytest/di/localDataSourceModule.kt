package com.davidpl.rickandmortytest.di

import com.davidpl.rickandmortytest.datasource.local.LocalCharactersDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val LOCAL_TARGETS_DATA_SOURCE = "LocalTargetsDataSource"

var localDataSourceModule = module {

    single(named(LOCAL_TARGETS_DATA_SOURCE)) {
        LocalCharactersDataSource()
    }

}