package com.davidpl.rickandmortytest.presenter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
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
                        HomeScreen(
                            paddingValues,
                            homeViewModel
                        )
                    }
                )
            }
        }
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