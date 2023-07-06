package com.davidpl.rickandmortytest.presenter.viewmodel

sealed class DetailDataIntent {

    class LoadChar(val characterId: Int): DetailDataIntent()

}