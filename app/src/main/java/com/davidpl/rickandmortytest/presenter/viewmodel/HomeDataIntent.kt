package com.davidpl.rickandmortytest.presenter.viewmodel

sealed class HomeDataIntent {

    object LoadCharacters: HomeDataIntent()

}