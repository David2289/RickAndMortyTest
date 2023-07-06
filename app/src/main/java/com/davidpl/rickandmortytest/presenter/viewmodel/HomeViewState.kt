package com.davidpl.rickandmortytest.presenter.viewmodel

import com.davidpl.rickandmortytest.presenter.model.TargetsDataModel

data class HomeViewState(
    val isInactive: Boolean,
    val isLoading: Boolean,
    val rankedTargetLoaded: TargetsDataModel?,
    val rankedTargetError: Boolean,
)
{
    companion object {
    fun initial() = HomeViewState(
        isInactive = true,
        isLoading = false,
        rankedTargetLoaded = null,
        rankedTargetError = false
    )
}
}