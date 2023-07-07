package com.davidpl.rickandmortytest.datasource.model

import com.davidpl.rickandmortytest.presenter.model.CharacterResultModel
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class CharacterResultApiModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("species")
    val species: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("origin")
    val origin: CharacterLocationApiModel,

    @SerializedName("location")
    val location: CharacterLocationApiModel,

    @SerializedName("image")
    val image: String,

    @SerializedName("episode")
    val episode: List<String>,

    @SerializedName("url")
    val url: String,

    @SerializedName("created")
    val created: String
)

fun CharacterResultApiModel.toDomain(): CharacterResultModel {
    return CharacterResultModel(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.toDomain(),
        location = location.toDomain(),
        image = image,
        episodeJson = Gson().toJson(episode),
        url = url,
        created = created
    )
}