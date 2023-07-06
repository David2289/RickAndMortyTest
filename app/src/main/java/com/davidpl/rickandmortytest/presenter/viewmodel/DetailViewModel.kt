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

class DetailViewModel(
    private val charactersUseCase: CharactersUseCase
): ViewModel() {

    val dataIntent = Channel<DetailDataIntent>(Channel.UNLIMITED)

    private val _viewState = MutableStateFlow(DetailViewState.initial())
    val viewState: StateFlow<DetailViewState> = _viewState

    init {
        viewModelScope.launch {
            dataIntent
                .consumeAsFlow()
                .onEach {
                    when (it) {
                        is DetailDataIntent.LoadChar -> {
                            retrieveChar(it.characterId)
                        }
                    }
                }
                .collect()
        }
    }

    private fun retrieveChar(characterId: Int) {
        _viewState.update {
            it.copy(isInactive = false, isLoading = true)
        }
        viewModelScope.launch {
            charactersUseCase
                .retrieveCharUseCase
                .invoke(characterId)
                .onEach { dataSate ->
                    when (dataSate) {
                        is DataState.Success -> {
                            _viewState.update {
                                it.copy(isInactive = false, isLoading = false, charLoaded = dataSate.data)
                            }
                        }
                        else -> {
                            _viewState.update {
                                it.copy(isInactive = false, isLoading = false, charError = true)
                            }
                        }
                    }
                }
                .collect()
        }
    }

}