package com.davidpl.rickandmortytest.presenter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.davidpl.rickandmortytest.R
import com.davidpl.rickandmortytest.presenter.ui.states.AppBarState
import com.davidpl.rickandmortytest.presenter.ui.states.DetailRoute
import com.davidpl.rickandmortytest.presenter.ui.states.HomeRoute
import com.davidpl.rickandmortytest.presenter.ui.states.rememberAppBarState
import com.davidpl.rickandmortytest.presenter.viewmodel.DetailViewModel
import com.davidpl.rickandmortytest.presenter.viewmodel.HomeViewModel
import com.davidpl.rickandmortytest.presenter.ui.theme.Typography
import com.davidpl.rickandmortytest.presenter.ui.theme.BlackWhite
import com.davidpl.rickandmortytest.presenter.ui.theme.RickAndMortyTestTheme
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {

    private val homeViewModel = get<HomeViewModel>()
    private val detailViewModel = get<DetailViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTestTheme {
                val navController = rememberNavController()
                val appBarState = rememberAppBarState(
                    navController,
                )
                var isFilterApplied by remember {
                    mutableStateOf(false)
                }
                Scaffold(
                    topBar = {
                        MainTopBar(
                            appBarState,
                            isFilterApplied
                        )
                    },
                    content = { paddingValues ->
                        NavigationHost(
                            paddingValues,
                            appBarState,
                            navController,
                            homeViewModel,
                            onFilterChange = { isFilterApplied = it }
                        )
                    }
                )
            }
        }
    }

    @Composable
    fun NavigationHost(
        paddingValues: PaddingValues,
        appBarState: AppBarState,
        navController: NavHostController,
        homeViewModel: HomeViewModel,
        onFilterChange: (isFilterApplied: Boolean) -> Unit
    ) {

        NavHost(
            navController = navController,
            startDestination = HomeRoute
        ) {
            composable(HomeRoute) {
                HomeScreen(
                    paddingValues,
                    appBarState,
                    homeViewModel,
                    navController,
                    onFilterChange
                )
            }
            composable(
                route = "$DetailRoute?charId={$charId}",
                arguments = listOf(
                    navArgument(charId) {
                        defaultValue = 0
                        type = NavType.IntType
                    }
                )
            ) {
                DetailScreen(
                    paddingValues,
                    appBarState,
                    detailViewModel,
                    onNavBackPressed = { navController.popBackStack() },
                    it.arguments?.getInt(charId) ?: 0
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainTopBar(
        appBarState: AppBarState,
        isFilterApplied: Boolean
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            title = {
                Text(
                    appBarState.title,
                    color = BlackWhite,
                    style = Typography.bodyMedium
                )
            },
            navigationIcon = {
                val navigationIcon = appBarState.navigationIcon
                if (navigationIcon != null) {
                    IconButton(onClick = {
                        navigationIcon.onClick.invoke()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = navigationIcon.iconRes),
                            contentDescription = navigationIcon.contentDescription,
                            tint = navigationIcon.iconTint
                        )
                    }
                }
            },
            actions = {
                appBarState.actions.forEach { appBarMenuItem ->
                    val iconVector = ImageVector.vectorResource(id = if (isFilterApplied) R.drawable.ic_close_black_16dp else appBarMenuItem.iconRes)
                    IconButton(onClick = { appBarMenuItem.onClick.invoke() }) {
                        Icon(
                            imageVector = iconVector,
                            contentDescription = appBarMenuItem.contentDescription,
                            tint = appBarMenuItem.iconColor
                        )
                    }
                }
            }
        )
    }

    companion object {
        const val charId = "charId"
    }

}