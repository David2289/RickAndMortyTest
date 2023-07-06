package com.davidpl.rickandmortytest.datasource.model

import com.davidpl.rickandmortytest.presenter.model.CharacterLocationModel
import com.google.gson.annotations.SerializedName

data class CharacterLocationApiModel(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)

fun CharacterLocationApiModel.toDomain() =
    CharacterLocationModel(
        name = name,
        url = url
    )