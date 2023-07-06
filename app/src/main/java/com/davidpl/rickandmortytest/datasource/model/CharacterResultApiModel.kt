package com.davidpl.rickandmortytest.datasource.model

import com.davidpl.rickandmortytest.presenter.model.CharacterResultModel
import com.davidpl.rickandmortytest.presenter.model.CharacterStatus
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
    val domainStatus = when (status) {
        "Alive" -> CharacterStatus.ALIVE
        "unknown" -> CharacterStatus.UNKNOWN
        "Dead" -> CharacterStatus.DEAD
        else -> CharacterStatus.UNKNOWN
    }
    return CharacterResultModel(
        id = id,
        name = name,
        status = status,
        statusColor = domainStatus,
        species = species,
        type = type,
        gender = gender,
        origin = origin.toDomain(),
        location = location.toDomain(),
        image = image,
        episode = episode,
        url = url,
        created = created
    )
}