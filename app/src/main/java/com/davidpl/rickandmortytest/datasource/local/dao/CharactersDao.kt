package com.davidpl.rickandmortytest.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davidpl.rickandmortytest.presenter.model.CharacterResultModel

@Dao
interface CharactersDao {

    @Query("SELECT * FROM table_characters")
    fun getCharacters(): List<CharacterResultModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<CharacterResultModel>)

}