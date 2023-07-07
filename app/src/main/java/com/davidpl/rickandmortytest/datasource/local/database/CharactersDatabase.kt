package com.davidpl.rickandmortytest.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidpl.rickandmortytest.datasource.local.dao.CharactersDao
import com.davidpl.rickandmortytest.presenter.model.CharacterResultModel

@Database(
    entities = [CharacterResultModel::class],
    version = 1,
    exportSchema = false
)
abstract class CharactersDatabase: RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

}

const val CHARACTERS_DATABASE = "characters_database"