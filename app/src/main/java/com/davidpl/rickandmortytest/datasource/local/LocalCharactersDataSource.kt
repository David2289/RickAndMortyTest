package com.davidpl.rickandmortytest.datasource.local

import com.davidpl.rickandmortytest.datasource.local.dao.CharactersDao
import com.davidpl.rickandmortytest.states.DataState
import com.davidpl.rickandmortytest.presenter.model.CharacterInfoModel
import com.davidpl.rickandmortytest.presenter.model.CharacterResultModel
import com.davidpl.rickandmortytest.presenter.model.CharactersModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalCharactersDataSource(
    private val charactersDao: CharactersDao
) {

    suspend fun retrieveCharacters(): DataState<CharactersModel> {
        val characters = withContext(Dispatchers.IO) { charactersDao.getCharacters() }
        return DataState.Success(
            CharactersModel(
                info = CharacterInfoModel(0, 0, "", ""),
                results = characters
            )
        )
    }

    suspend fun insertCharacters(characters: List<CharacterResultModel>) {
        withContext(Dispatchers.IO) { charactersDao.insertCharacters(characters) }
    }

}