package com.davidpl.rickandmortytest.presenter.ui.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Stable
class AppBarState(
    private val navController: NavController,
    scope: CoroutineScope,
) {

    init {
        navController.currentBackStackEntryFlow
            .distinctUntilChanged()
            .onEach { backStackEntry ->
                val route = backStackEntry.destination.route
                currentScreen = getScreen(route)
            }
            .launchIn(scope)
    }

    var currentScreen by mutableStateOf<Screen?>(null)
        private set

    val isVisible: Boolean
        @Composable get() = currentScreen?.isAppBarVisible == true

    val navigationIcon: Screen.NavigationIcon?
        @Composable get() = currentScreen?.navigationIcon

    val title: String
        @Composable get() = currentScreen?.title.orEmpty()

    val actions: List<AppBarMenuItem>
        @Composable get() = currentScreen?.actions.orEmpty()
}


@Composable
fun rememberAppBarState(
    navController: NavController,
    scope: CoroutineScope = rememberCoroutineScope(),
) = remember {
    AppBarState(
        navController,
        scope,
    )
}


fun getScreen(route: String?): Screen? = when {
    route == null -> null
    route.substringBefore('?') == HomeRoute -> Screen.Home()
    route.substringBefore('?') == DetailRoute -> Screen.Detail()
    else -> null
}