package com.davidpl.rickandmortytest.presenter.model

import com.davidpl.rickandmortytest.ui.theme.DustyGray
import com.davidpl.rickandmortytest.ui.theme.Shamrock
import com.davidpl.rickandmortytest.ui.theme.Thunderbird

data class CharacterResultModel(
    val id: Int,
    val name: String,
    val status: String,
    val statusColor: CharacterStatus,
    val species: String,
    val type: String,
    val gender: String,
    val origin: CharacterLocationModel,
    val location: CharacterLocationModel,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) {
    val indicatorColor = when (statusColor) {
        CharacterStatus.UNKNOWN -> DustyGray
        CharacterStatus.ALIVE -> Shamrock
        CharacterStatus.DEAD -> Thunderbird
    }
}