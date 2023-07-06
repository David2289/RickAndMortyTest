package com.davidpl.rickandmortytest.presenter.viewmodel

import com.davidpl.rickandmortytest.presenter.model.CharacterInfoModel
import com.davidpl.rickandmortytest.presenter.model.CharactersModel

data class HomeViewState(
    val isInactive: Boolean,
    val isLoading: Boolean,
    val charactersLoaded: CharactersModel,
    val charactersError: Boolean,
)
{
    companion object {
        fun initial() = HomeViewState(
            isInactive = true,
            isLoading = false,
            charactersLoaded = CharactersModel(
                info = CharacterInfoModel(0, 0, "", ""),
                results = listOf()
            ),
            charactersError = false
        )
    }
}