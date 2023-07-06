package com.davidpl.rickandmortytest

import android.app.Application
import com.davidpl.rickandmortytest.di.localDataSourceModule
import com.davidpl.rickandmortytest.di.remoteDataSourceModule
import com.davidpl.rickandmortytest.di.repositoryModule
import com.davidpl.rickandmortytest.di.useCaseModule
import com.davidpl.rickandmortytest.di.viewModelModule
import org.koin.android.ext.koin.androidContext

class RickAndMortyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@RickAndMortyApplication)
            modules(
                listOf(
                    viewModelModule,
                    useCaseModule,
                    repositoryModule,
                    localDataSourceModule,
                    remoteDataSourceModule
                )
            )
        }
    }

}