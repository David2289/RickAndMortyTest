package com.davidpl.rickandmortytest.presenter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.davidpl.rickandmortytest.presenter.viewmodel.HomeDataIntent
import com.davidpl.rickandmortytest.presenter.viewmodel.HomeViewModel
import com.davidpl.rickandmortytest.ui.theme.BlackWhite
import com.davidpl.rickandmortytest.ui.theme.DustyGray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel
) {

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main.immediate) {
            viewModel.dataIntent.send(HomeDataIntent.LoadCharacters)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackWhite)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Home Screen",
            fontWeight = FontWeight.Bold,
            color = DustyGray,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}