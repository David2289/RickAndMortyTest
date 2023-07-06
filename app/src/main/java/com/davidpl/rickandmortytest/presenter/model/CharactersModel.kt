package com.davidpl.rickandmortytest.presenter.model

data class CharactersModel(
    val info: CharacterInfoModel,
    val results: List<CharacterResultModel>,
)