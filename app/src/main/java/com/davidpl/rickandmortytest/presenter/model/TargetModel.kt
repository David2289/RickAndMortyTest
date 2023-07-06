package com.davidpl.rickandmortytest.presenter.model

data class TargetModel(
    val id: Int,
    val registerDate: String,
    val name: String,
    val chain: String,
    val sector: String,
    val type: String,
    val subtype: String,
    val webUrl: String,
    val addressType: String,
    val addressName: String,
    val addressNumber: Int,
    val addressLatitude: Long,
    val addressLongitude: Long,
    val starsCountOne: Long,
    val starsCountTwo: Long,
    val starsCountThree: Long,
    val starsCountFour: Long,
    val starsCountFive: Long,
    val starsCountTotal: Long,
    val starsRank: Double
)