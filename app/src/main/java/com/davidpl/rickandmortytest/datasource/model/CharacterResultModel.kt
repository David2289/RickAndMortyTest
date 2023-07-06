package com.davidpl.rickandmortytest.datasource.model

data class CharacterResultModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: CharacterLocationModel,
    val location: CharacterLocationModel,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)