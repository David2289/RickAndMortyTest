package com.davidpl.rickandmortytest.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidpl.rickandmortytest.domain.CharactersUseCase
import com.davidpl.rickandmortytest.states.DataState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val charactersUseCase: CharactersUseCase
): ViewModel() {

    val dataIntent = Channel<HomeDataIntent>(Channel.UNLIMITED)

    private val _viewState = MutableStateFlow(HomeViewState.initial())
    val viewState: StateFlow<HomeViewState> = _viewState

    init {
        viewModelScope.launch {
            dataIntent
                .consumeAsFlow()
                .onEach {
                    when (it) {
                        is HomeDataIntent.LoadCharacters -> {
                            retrieveCharacters()
                        }
                    }
                }
                .collect()
        }
    }

    private fun retrieveCharacters() {
        _viewState.update {
            it.copy(isInactive = false, isLoading = true)
        }
        viewModelScope.launch {
            charactersUseCase
                .retrieveCharactersUseCase
                .invoke()
                .onEach { dataSate ->
                    when (dataSate) {
                        is DataState.Success -> {
                            _viewState.update {
                                it.copy(isInactive = false, isLoading = false, charactersLoaded = dataSate.data)
                            }
                            println("RICK_TESTING retrieved list: ${dataSate.data.results}")
                        }
                        is DataState.Failure -> {
                            _viewState.update {
                                it.copy(isInactive = false, isLoading = false, charactersError = true)
                            }
                            println("RICK_TESTING failure exception: ${dataSate.data.exception}")
                        }
                        else -> {
                            _viewState.update {
                                it.copy(isInactive = false, isLoading = false, charactersError = true)
                            }
                            println("RICK_TESTING error")
                        }
                    }
                }
                .collect()
        }
    }

}