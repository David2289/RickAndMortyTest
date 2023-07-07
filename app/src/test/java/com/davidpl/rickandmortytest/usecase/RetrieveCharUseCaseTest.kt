package com.davidpl.rickandmortytest.usecase

import com.davidpl.rickandmortytest.domain.RetrieveCharUseCase
import com.davidpl.rickandmortytest.presenter.model.CharacterInfoModel
import com.davidpl.rickandmortytest.presenter.model.CharacterLocationModel
import com.davidpl.rickandmortytest.presenter.model.CharacterResultModel
import com.davidpl.rickandmortytest.presenter.model.CharacterStatus
import com.davidpl.rickandmortytest.presenter.model.CharactersModel
import com.davidpl.rickandmortytest.repository.CharactersRepository
import com.davidpl.rickandmortytest.states.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RetrieveCharUseCaseTest {

    private lateinit var retrieveCharUseCase: RetrieveCharUseCase

    @Mock
    private lateinit var charactersRepository: CharactersRepository

    @Before
    fun setup() {
        retrieveCharUseCase = RetrieveCharUseCase(charactersRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `validate retrieve char use case return single char result`() = runTest {
        val charId = 1
        val charactersModel = getCharactersResult()
        val characterAdIndex1 = getCharactersResult().results.find { it.id == charId }

        Mockito
            .`when`(charactersRepository.retrieveCharacters(true))
            .thenReturn(DataState.Success(charactersModel))

        val firstSentence = retrieveCharUseCase.invoke(charId).first()
        assertEquals(DataState.Success(characterAdIndex1), firstSentence)
    }

    private fun getCharactersResult() =
        CharactersModel(
            info = CharacterInfoModel(10, 1, "", null),
            results = listOf(
                CharacterResultModel(
                    1,
                    "Rick",
                    "",
                    CharacterStatus.ALIVE,
                    "",
                    "",
                    "",
                    CharacterLocationModel("", ""),
                    CharacterLocationModel("", ""),
                    "",
                    listOf(),
                    "",
                    ""
                ),
                CharacterResultModel(
                    2,
                    "Morty",
                    "",
                    CharacterStatus.ALIVE,
                    "",
                    "",
                    "",
                    CharacterLocationModel("", ""),
                    CharacterLocationModel("", ""),
                    "",
                    listOf(),
                    "",
                    ""
                )
            )
        )

}