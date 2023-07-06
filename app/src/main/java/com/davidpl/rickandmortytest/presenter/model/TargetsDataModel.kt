package com.davidpl.rickandmortytest.presenter.model

data class TargetsDataModel(
    val data: List<TargetModel>,
    val total: Int,
    val hasMore: Boolean
)