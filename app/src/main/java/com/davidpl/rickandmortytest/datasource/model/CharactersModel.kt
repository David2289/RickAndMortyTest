package com.davidpl.rickandmortytest.datasource.model

data class CharactersModel(
    val info: CharacterInfoModel,
    val results: List<CharacterResultModel>,
)