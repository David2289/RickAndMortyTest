package com.davidpl.rickandmortytest.repository

import com.davidpl.rickandmortytest.datasource.local.LocalCharactersDataSource
import com.davidpl.rickandmortytest.datasource.remote.RemoteCharactersDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersRepositoryUnitTest {

    private lateinit var charactersRepository: CharactersRepository

    @Mock
    private lateinit var localCharactersDataSource: LocalCharactersDataSource

    @Mock
    private lateinit var remoteCharactersDataSource: RemoteCharactersDataSource

    @Before
    fun setup() {
        charactersRepository = CharactersRepository(localCharactersDataSource, remoteCharactersDataSource)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `validate users repository pattern from remote`() = runTest {
        charactersRepository.retrieveCharacters(fromRemote = true)
        Mockito.verify(remoteCharactersDataSource, Mockito.times(1)).retrieveCharacters()
    }

    //Required to use runTest method to execute suspend functions
    @ExperimentalCoroutinesApi
    @Test
    fun `validate users repository pattern from local`() = runTest {
        val page = 0
        charactersRepository.retrieveCharacters(fromRemote = false)
        Mockito.verify(localCharactersDataSource, Mockito.times(1)).retrieveCharacters()
    }

}