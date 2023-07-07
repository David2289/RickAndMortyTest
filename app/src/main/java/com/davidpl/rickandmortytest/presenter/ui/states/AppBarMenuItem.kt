package com.davidpl.rickandmortytest.presenter.ui.states

import androidx.compose.ui.graphics.vector.ImageVector

data class AppBarMenuItem(
    val title: String,
    val icon: ImageVector,
    val contentDescription: String?,
    val onClick: () -> Unit
)