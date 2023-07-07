package com.davidpl.rickandmortytest.di

import android.content.Context
import androidx.room.Room
import com.davidpl.rickandmortytest.datasource.local.LocalCharactersDataSource
import com.davidpl.rickandmortytest.datasource.local.database.CHARACTERS_DATABASE
import com.davidpl.rickandmortytest.datasource.local.database.CharactersDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val CHARACTERS_DAO = "CharactersDao"
internal const val LOCAL_CHARACTERS_DATA_SOURCE = "LocalCharactersDataSource"

var localDataSourceModule = module {

    single(named(CHARACTERS_DAO)) { charactersDatabase(androidContext()).charactersDao() }
    single(named(LOCAL_CHARACTERS_DATA_SOURCE)) {
        LocalCharactersDataSource(get(named(CHARACTERS_DAO)))
    }

}

private fun charactersDatabase(context: Context): CharactersDatabase {
    return Room.databaseBuilder(context, CharactersDatabase::class.java, CHARACTERS_DATABASE)
        .allowMainThreadQueries()
        .build()
}