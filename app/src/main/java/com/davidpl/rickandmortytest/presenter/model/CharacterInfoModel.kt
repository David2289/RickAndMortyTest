package com.davidpl.rickandmortytest.presenter.model

data class CharacterInfoModel(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String?
)