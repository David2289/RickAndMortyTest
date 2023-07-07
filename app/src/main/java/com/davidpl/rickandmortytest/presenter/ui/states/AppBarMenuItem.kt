package com.davidpl.rickandmortytest.presenter.ui.states

import androidx.compose.ui.graphics.Color

data class AppBarMenuItem(
    val title: String,
    val iconRes: Int,
    val iconColor: Color,
    val contentDescription: String?,
    val onClick: () -> Unit
)