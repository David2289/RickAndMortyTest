package com.davidpl.rickandmortytest.di

import com.davidpl.rickandmortytest.presenter.viewmodel.DetailViewModel
import com.davidpl.rickandmortytest.presenter.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

var viewModelModule = module {

    viewModel { HomeViewModel(
        get(named(CHARACTERS_USE_CASE))
    ) }
    viewModel { DetailViewModel(
        get(named(CHARACTERS_USE_CASE))
    ) }

}