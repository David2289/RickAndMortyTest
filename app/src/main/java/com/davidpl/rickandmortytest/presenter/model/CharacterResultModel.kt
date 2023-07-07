package com.davidpl.rickandmortytest.presenter.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.davidpl.rickandmortytest.presenter.ui.theme.DustyGray
import com.davidpl.rickandmortytest.presenter.ui.theme.Shamrock
import com.davidpl.rickandmortytest.presenter.ui.theme.Thunderbird
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "table_characters")
data class CharacterResultModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "species")
    val species: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "gender")
    val gender: String,

    @Embedded(prefix = "origin_")
    val origin: CharacterLocationModel,

    @Embedded(prefix = "location_")
    val location: CharacterLocationModel,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "episodeJson")
    val episodeJson: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "created")
    val created: String
) {

    val episode: List<String>
        get() = Gson().fromJson(episodeJson, object: TypeToken<List<String>>() {}.type)
}

fun CharacterResultModel.getStatusColor() = when (status) {
    "Alive" -> CharacterStatus.ALIVE
    "unknown" -> CharacterStatus.UNKNOWN
    "Dead" -> CharacterStatus.DEAD
    else -> CharacterStatus.UNKNOWN
}

fun CharacterResultModel.getIndicatorColor() = when (getStatusColor()) {
    CharacterStatus.UNKNOWN -> DustyGray
    CharacterStatus.ALIVE -> Shamrock
    CharacterStatus.DEAD -> Thunderbird
}