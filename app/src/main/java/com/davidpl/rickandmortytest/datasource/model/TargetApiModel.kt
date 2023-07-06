package com.davidpl.rickandmortytest.datasource.model

import com.davidpl.rickandmortytest.presenter.model.TargetModel
import com.google.gson.annotations.SerializedName

data class TargetApiModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("register_date")
    val registerDate: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("chain")
    val chain: String,

    @SerializedName("sector")
    val sector: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("subtype")
    val subtype: String,

    @SerializedName("web_url")
    val webUrl: String,

    @SerializedName("address_type")
    val addressType: String,

    @SerializedName("address_name")
    val addressName: String,

    @SerializedName("address_number")
    val addressNumber: Int,

    @SerializedName("address_latitude")
    val addressLatitude: Long,

    @SerializedName("address_longitude")
    val addressLongitude: Long,

    @SerializedName("stars_count_one")
    val starsCountOne: Long,

    @SerializedName("stars_count_two")
    val starsCountTwo: Long,

    @SerializedName("stars_count_three")
    val starsCountThree: Long,

    @SerializedName("stars_count_four")
    val starsCountFour: Long,

    @SerializedName("stars_count_five")
    val starsCountFive: Long,

    @SerializedName("stars_count_total")
    val starsCountTotal: Long,

    @SerializedName("stars_rank")
    val starsRank: Double

)

fun TargetApiModel.toDomain() =
    TargetModel(
        id,
        registerDate,
        name,
        chain,
        sector,
        type,
        subtype,
        webUrl,
        addressType,
        addressName,
        addressNumber,
        addressLatitude,
        addressLongitude,
        starsCountOne,
        starsCountTwo,
        starsCountThree,
        starsCountFour,
        starsCountFive,
        starsCountTotal,
        starsRank
    )
