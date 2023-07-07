package com.davidpl.rickandmortytest.presenter.viewmodel

import com.davidpl.rickandmortytest.presenter.model.CharacterLocationModel
import com.davidpl.rickandmortytest.presenter.model.CharacterResultModel

data class DetailViewState(
    val isInactive: Boolean,
    val isLoading: Boolean,
    val charLoaded: CharacterResultModel,
    val charError: Boolean,
)
{
    companion object {
        fun initial() = DetailViewState(
            isInactive = true,
            isLoading = false,
            charLoaded = CharacterResultModel(
                id = 0,
                name = "",
                status = "",
                species = "",
                type = "",
                gender = "",
                origin = CharacterLocationModel("", ""),
                location = CharacterLocationModel("", ""),
                image = "",
                episodeJson = "",
                url = "",
                created = ""
            ),
            charError = false
        )
    }
}