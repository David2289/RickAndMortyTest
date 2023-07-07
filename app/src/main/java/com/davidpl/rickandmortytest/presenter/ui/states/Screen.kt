package com.davidpl.rickandmortytest.presenter.ui.states

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import com.davidpl.rickandmortytest.R
import com.davidpl.rickandmortytest.presenter.ui.theme.White
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

const val HomeRoute = "home"
const val DetailRoute = "detail"

sealed interface Screen {
    val route: String
    val isAppBarVisible: Boolean
    val navigationIcon: NavigationIcon?
    val title: String
    val actions: List<AppBarMenuItem>

    data class NavigationIcon(
        val iconRes: Int,
        val iconTint: Color,
        val contentDescription: String?,
        val onClick: () -> Unit
    )

    class Home: Screen {
        override val route: String = HomeRoute
        override val isAppBarVisible: Boolean = true
        override val navigationIcon: NavigationIcon? = null
        override val title: String = "Home"
        override val actions: List<AppBarMenuItem> = listOf(
            AppBarMenuItem(
                title = "Settings",
                onClick = {
                    _buttons.tryEmit(AppBarIcons.Settings)
                },
                icon = Icons.Filled.Settings,
                contentDescription = null,
            )
        )

        enum class AppBarIcons {
            Settings
        }

        private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
        val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()
    }

    class Detail: Screen {
        override val route: String = DetailRoute
        override val isAppBarVisible: Boolean = true
        override val navigationIcon: NavigationIcon = NavigationIcon(
            iconRes = R.drawable.ic_arrow_left_white_16dp,
            iconTint = White,
            contentDescription = "Back",
            onClick = {
                println("RICK_TEST Screen Detail NavigationIcon onClick")
                _buttons.tryEmit(AppBarIcons.NavigationIcon)
            }
        )
        override val title: String = "Detail"
        override val actions: List<AppBarMenuItem> = listOf(
            AppBarMenuItem(
                title = "Settings",
                onClick = {
                    _buttons.tryEmit(AppBarIcons.Settings)
                },
                icon = Icons.Filled.Settings,
                contentDescription = null,
            )
        )

        enum class AppBarIcons {
            NavigationIcon,
            Settings
        }

        private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
        val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()
    }
}