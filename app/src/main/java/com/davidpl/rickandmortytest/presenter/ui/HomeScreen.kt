package com.davidpl.rickandmortytest.presenter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.davidpl.rickandmortytest.R
import com.davidpl.rickandmortytest.presenter.model.CharacterResultModel
import com.davidpl.rickandmortytest.presenter.model.getIndicatorColor
import com.davidpl.rickandmortytest.presenter.ui.states.AppBarState
import com.davidpl.rickandmortytest.presenter.ui.states.DetailRoute
import com.davidpl.rickandmortytest.presenter.ui.states.Screen
import com.davidpl.rickandmortytest.presenter.viewmodel.HomeDataIntent
import com.davidpl.rickandmortytest.presenter.viewmodel.HomeViewModel
import com.davidpl.rickandmortytest.presenter.ui.theme.Black
import com.davidpl.rickandmortytest.presenter.ui.theme.DustyGray
import com.davidpl.rickandmortytest.presenter.ui.theme.Melrose
import com.davidpl.rickandmortytest.presenter.ui.theme.Typography
import com.davidpl.rickandmortytest.presenter.ui.theme.White
import com.davidpl.rickandmortytest.utility.CharactersError
import com.davidpl.rickandmortytest.utility.Loader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    appBarState: AppBarState,
    viewModel: HomeViewModel,
    navController: NavController,
    onFilterChange: (isFilterApplied: Boolean) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(viewModel.filterTextValue))
    }

    var isFilterApplied by remember {
        mutableStateOf(viewModel.filterTextValue.isNotEmpty())
    }

    val screen = appBarState.currentScreen as? Screen.Home
    LaunchedEffect(key1 = screen) {
        screen?.buttons?.onEach { button ->
            when (button) {
                Screen.Home.AppBarIcons.Filter -> {
                    if (isFilterApplied) {
                        isFilterApplied = false
                        viewModel.filterTextValue = ""
                        onFilterChange.invoke(false)
                    } else {
                        showDialog = showDialog.not()
                    }
                }
            }
        }?.launchIn(this)
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main.immediate) {
            viewModel.dataIntent.send(HomeDataIntent.LoadCharacters)
        }
    }

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    val listResult = when {
        isFilterApplied -> {
            when {
                textFieldValue.text.isEmpty() -> viewState.charactersLoaded.results
                else -> {
                    viewState.charactersLoaded.results.filter { it.name.contains(textFieldValue.text, ignoreCase = true) }
                }
            }
        }
        else -> { viewState.charactersLoaded.results }

    }

    when {
        viewState.isInactive -> {}
        viewState.isLoading -> { Loader() }
        viewState.charactersError -> { CharactersError() }
        else -> {
            CharactersContent(
                paddingValues,
                listResult,
                navController
            )
        }
    }
    if (showDialog) {
        isFilterApplied = false
        Dialog(onDismissRequest = { showDialog = false }) {
            Column(
                Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .background(White)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 20.dp, 20.dp, 0.dp),
                    text = stringResource(id = R.string.home_filter_dialog_desc),
                    style = Typography.bodyMedium
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 20.dp, 20.dp, 0.dp),
                    value = textFieldValue,
                    onValueChange = { newText ->
                        textFieldValue = newText
                    }
                )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp, 20.dp, 0.dp)) {
                    TextButton(onClick = { showDialog = false }) {
                        Text(
                            text = stringResource(id = R.string.home_filter_dialog_cancel),
                            style = Typography.bodyMedium,
                            color = Melrose
                        )
                    }
                    TextButton(
                        onClick = {
                            viewModel.filterTextValue = textFieldValue.text
                            isFilterApplied = true
                            onFilterChange.invoke(true)
                            showDialog = false
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.home_filter_dialog_apply),
                            style = Typography.bodyMedium,
                            color = Melrose
                        )
                    }
                }
            }
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
fun CharacterItem(
    character: CharacterResultModel,
    navController: NavController
) {
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
                        .background(character.getIndicatorColor()),
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