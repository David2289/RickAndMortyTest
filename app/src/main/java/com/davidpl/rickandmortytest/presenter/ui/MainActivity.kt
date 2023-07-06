package com.davidpl.rickandmortytest.presenter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.davidpl.rickandmortytest.R
import com.davidpl.rickandmortytest.presenter.viewmodel.HomeViewModel
import com.davidpl.rickandmortytest.ui.theme.Typography
import com.davidpl.rickandmortytest.ui.theme.BlackWhite
import com.davidpl.rickandmortytest.ui.theme.RickAndMortyTestTheme
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {

    private val homeViewModel = get<HomeViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTestTheme {
                Scaffold(
                    topBar = { MainTopBar() },
                    content = { paddingValues ->
                        NavigationHost(
                            paddingValues,
                            homeViewModel
                        )
                    }
                )
            }
        }
    }

    @Composable
    fun NavigationHost(paddingValues: PaddingValues, homeViewModel: HomeViewModel) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = SCREEN_CHARACTERS_LIST
        ) {
            composable(SCREEN_CHARACTERS_LIST) {
                HomeScreen(
                    paddingValues,
                    homeViewModel,
                    navController
                )
            }
            composable(
                route = "$SCREEN_CHAR_DETAIL?cardId={$CHAR_SELECTED_ID}",
                arguments = listOf(
                    navArgument(CHAR_SELECTED_ID) {
                        defaultValue = 0
                        type = NavType.IntType
                    }
                )
            ) {
                HomeScreen(
                    paddingValues,
                    homeViewModel,
                    navController
                )
            }
        }
    }

    companion object {
        const val SCREEN_CHARACTERS_LIST = "SCREEN_CHARACTERS_LIST"
        const val SCREEN_CHAR_DETAIL = "SCREEN_CHAR_DETAIL"
        const val CHAR_SELECTED_ID = "CHAR_SELECTED_ID"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainTopBar() {
        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            title = {
                Text(
                    stringResource(id = R.string.app_name),
                    color = BlackWhite,
                    style = Typography.bodyLarge
                )
            }
        )
    }
}