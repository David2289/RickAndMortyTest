package com.davidpl.rickandmortytest.presenter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.davidpl.rickandmortytest.R
import com.davidpl.rickandmortytest.presenter.model.CharacterResultModel
import com.davidpl.rickandmortytest.presenter.ui.states.AppBarState
import com.davidpl.rickandmortytest.presenter.ui.states.DetailRoute
import com.davidpl.rickandmortytest.presenter.viewmodel.HomeDataIntent
import com.davidpl.rickandmortytest.presenter.viewmodel.HomeViewModel
import com.davidpl.rickandmortytest.presenter.ui.theme.Black
import com.davidpl.rickandmortytest.presenter.ui.theme.DustyGray
import com.davidpl.rickandmortytest.presenter.ui.theme.Typography
import com.davidpl.rickandmortytest.utility.CharactersError
import com.davidpl.rickandmortytest.utility.Loader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    appBarState: AppBarState,
    viewModel: HomeViewModel,
    navController: NavController
) {

//    val screen = appBarState.currentScreen as? Screen.Home
//    LaunchedEffect(key1 = screen) {
//        screen?.buttons?.onEach { button ->
//            when (button) {
//                Screen.Home.AppBarIcons.Settings -> onSettingsClick()
//            }
//        }?.launchIn(this)
//    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main.immediate) {
            viewModel.dataIntent.send(HomeDataIntent.LoadCharacters)
        }
    }

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    when {
        viewState.isInactive -> {}
        viewState.isLoading -> { Loader() }
        viewState.charactersError -> { CharactersError() }
        else -> {
            CharactersContent(
                paddingValues,
                viewState.charactersLoaded.results,
                navController
            )
        }
    }
}

@Composable
fun CharactersContent(
    paddingValues: PaddingValues,
    characterList: List<CharacterResultModel>,
    navController: NavController
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        items(
            items = characterList,
            itemContent = {
                CharacterItem(it, navController)
            }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterItem(character: CharacterResultModel, navController: NavController) {
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(25.dp, 20.dp)
        .clickable {
            navController.navigate(
                "${DetailRoute}?charId=${character.id}"
            )
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = character.image,
            contentDescription = null,
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .clip(CircleShape)
        ) {
            it
                .error(R.drawable.ic_close_black_16dp)
                .placeholder(R.drawable.ic_search_gray)
                .load(character.image)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = character.name,
                style = Typography.titleLarge,
                color = Black
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(character.indicatorColor),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = character.status,
                    style = Typography.bodyMedium,
                    color = Black
                )
            }
            Text(
                text = stringResource(id = R.string.home_first_seen_in),
                style = Typography.bodyMedium,
                color = DustyGray
            )
            Text(
                text = character.origin.name,
                style = Typography.bodyMedium,
                color = Black
            )
        }
    }
}