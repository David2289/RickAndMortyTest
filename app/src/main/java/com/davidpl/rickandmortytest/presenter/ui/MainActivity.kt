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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                Scaffold(
                    topBar = { MainTopBar(appBarState) },
                    content = { paddingValues ->
                        NavigationHost(
                            paddingValues,
                            appBarState,
                            navController,
                            homeViewModel
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
        homeViewModel: HomeViewModel
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
                    navController
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

    companion object {
        const val charId = "charId"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainTopBar(
        appBarState: AppBarState
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
            }
        )
    }
}