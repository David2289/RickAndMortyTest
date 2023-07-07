package com.davidpl.rickandmortytest.presenter.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.davidpl.rickandmortytest.presenter.model.getIndicatorColor
import com.davidpl.rickandmortytest.presenter.ui.states.AppBarState
import com.davidpl.rickandmortytest.presenter.ui.states.Screen
import com.davidpl.rickandmortytest.presenter.viewmodel.DetailDataIntent
import com.davidpl.rickandmortytest.presenter.viewmodel.DetailViewModel
import com.davidpl.rickandmortytest.presenter.ui.theme.Black
import com.davidpl.rickandmortytest.presenter.ui.theme.Typography
import com.davidpl.rickandmortytest.utility.CharactersError
import com.davidpl.rickandmortytest.utility.Loader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

@Composable
fun DetailScreen(
    paddingValues: PaddingValues,
    appBarState: AppBarState,
    viewModel: DetailViewModel,
    onNavBackPressed: () -> Unit,
    characterId: Int
) {

    val screen = appBarState.currentScreen as? Screen.Detail
    LaunchedEffect(key1 = screen) {
        screen?.buttons?.onEach { button ->
            when (button) {
                Screen.Detail.AppBarIcons.NavigationIcon -> { onNavBackPressed.invoke() }
                else -> {}
            }
        }?.launchIn(this)
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main.immediate) {
            viewModel.dataIntent.send(DetailDataIntent.LoadChar(characterId))
        }
    }

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    when {
        viewState.isInactive -> {}
        viewState.isLoading -> { Loader() }
        viewState.charError -> { CharactersError() }
        else -> {
            CharDetailContent(
                paddingValues,
                viewState.charLoaded
            )
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharDetailContent(
    paddingValues: PaddingValues,
    character: CharacterResultModel
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            GlideImage(
                model = character.image,
                contentDescription = null,
                modifier = Modifier
                    .width(155.dp)
                    .height(155.dp)
                    .padding(25.dp)
                    .clip(CircleShape)
                    .border(5.dp, color = character.getIndicatorColor(), shape = CircleShape)
            ) {
                it
                    .error(R.drawable.ic_close_black_16dp)
                    .placeholder(R.drawable.ic_search_gray)
                    .load(character.image)
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${character.name} (${character.status})",
                style = Typography.titleLarge,
                color = Black
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        DataItem(
            R.string.detail_first_seen_in,
            character.origin.name
        )
        DataItem(
            R.string.detail_last_seen_in,
            character.location.name
        )
        DataItem(
            R.string.detail_species,
            character.species
        )
        DataItem(
            R.string.detail_gender,
            character.gender
        )
        DataItem(
            R.string.detail_type,
            character.type
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun DataItem(
    headerTitle: Int,
    valueTitle: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(30.dp, 30.dp, 30.dp, 0.dp),
            text = stringResource(id = headerTitle),
            style = Typography.bodyMedium,
            color = Black
        )
        Text(
            modifier = Modifier.padding(30.dp, 10.dp, 30.dp, 0.dp),
            text = valueTitle.ifEmpty { "---" },
            style = Typography.bodyLarge,
            color = Black
        )
    }
}