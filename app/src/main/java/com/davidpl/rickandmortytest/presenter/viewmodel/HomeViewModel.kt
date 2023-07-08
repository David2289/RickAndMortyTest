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

    var filterTextValue = ""

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
                        }
                        is DataState.Failure -> {
                            _viewState.update {
                                it.copy(isInactive = false, isLoading = false, charactersError = true)
                            }
                        }
                        else -> {
                            _viewState.update {
                                it.copy(isInactive = false, isLoading = false, charactersError = true)
                            }
                        }
                    }
                }
                .collect()
        }
    }

}