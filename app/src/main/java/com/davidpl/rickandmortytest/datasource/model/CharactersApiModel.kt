package com.davidpl.rickandmortytest.datasource.model

import com.davidpl.rickandmortytest.presenter.model.CharactersModel
import com.google.gson.annotations.SerializedName

data class CharacterApiModel(
    @SerializedName("info")
    val info: CharacterInfoApiModel,

    @SerializedName("results")
    val results: List<CharacterResultApiModel>,
)

fun CharacterApiModel.toDomain() =
    CharactersModel(
        info = info.toDomain(),
        results = results.map { it.toDomain() }
    )