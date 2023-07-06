package com.davidpl.rickandmortytest.datasource.model

import com.davidpl.rickandmortytest.presenter.model.CharacterInfoModel
import com.google.gson.annotations.SerializedName

data class CharacterInfoApiModel(
    @SerializedName("count")
    val count: Int,

    @SerializedName("pages")
    val pages: Int,

    @SerializedName("next")
    val next: String,

    @SerializedName("prev")
    val prev: String?

)

fun CharacterInfoApiModel.toDomain() =
    CharacterInfoModel(
        count = count,
        pages = pages,
        next = next,
        prev = prev
    )