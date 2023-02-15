package com.example.camilo_romero_platzi.presentation.detail_viewmodel

import app.cash.turbine.test
import com.example.camilo_romero_platzi.CoroutinesTestRule
import com.example.camilo_romero_platzi.mocks.characterItemDetailMock
import com.example.camilo_romero_platzi.presentation.ui.detail.DetailViewModel
import com.example.camilo_romero_platzi.presentation.ui.detail.state.CharacterDetailsState
import com.example.domain.home.common.Resource
import com.example.domain.home.common.notDetailsErrorCode
import com.example.domain.home.common.unknownErrorCode
import com.example.domain.model.detail.CharacterDetailItem
import com.example.usecase.s.detail.GetCharacterDetailUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)

class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var detailsViewModel: DetailViewModel

    @Mock
    private lateinit var getCharacterDetailsUseCase: GetCharacterDetailUseCase

    @Before
    fun setUp() {
        detailsViewModel = DetailViewModel(getCharacterDetailsUseCase)
    }

    @Test
    fun `getCharacterDetail listen Flows emits a list of characters and change the UI state to LoadDetails`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Success(listOf(characterItemDetailMock))

            whenever(getCharacterDetailsUseCase.invoke(characterItemDetailMock.id)).thenReturn(
                flowOf(resultExpected)
            )

            detailsViewModel.getCharacterDetail(characterItemDetailMock.id)

            verify(getCharacterDetailsUseCase).invoke(characterItemDetailMock.id)

            getCharacterDetailsUseCase(characterItemDetailMock.id).collect {
                if (it is Resource.Success) Assert.assertEquals(resultExpected.data, it.data)
            }

            detailsViewModel.state.test {
                Assert.assertEquals(
                    CharacterDetailsState.LoadDetails(resultExpected.data!!), expectMostRecentItem()
                )
            }
        }
    }

    @Test
    fun `getCharacterDetail listen Flows emits a emptyList and change the UI state to Error`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Success(emptyList<CharacterDetailItem>())

            whenever(getCharacterDetailsUseCase.invoke(characterItemDetailMock.id)).thenReturn(
                flowOf(resultExpected)
            )

            detailsViewModel.getCharacterDetail(characterItemDetailMock.id)

            verify(getCharacterDetailsUseCase).invoke(characterItemDetailMock.id)

            getCharacterDetailsUseCase(characterItemDetailMock.id).collect {
                if (it is Resource.Error) Assert.assertEquals(resultExpected.data, it.data)
            }

            detailsViewModel.state.test {
                Assert.assertEquals(
                    CharacterDetailsState.Error(notDetailsErrorCode.toString()),
                    expectMostRecentItem()
                )
            }
        }
    }

    @Test
    fun `getCharacterDetail listen Flows emits a null and change the UI state to Error`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected =
                Resource.Error<List<CharacterDetailItem>>(unknownErrorCode.toString(), null)

            whenever(getCharacterDetailsUseCase.invoke(characterItemDetailMock.id)).thenReturn(
                flowOf(resultExpected)
            )

            detailsViewModel.getCharacterDetail(characterItemDetailMock.id)

            verify(getCharacterDetailsUseCase).invoke(characterItemDetailMock.id)

            getCharacterDetailsUseCase(characterItemDetailMock.id).collect {
                if (it is Resource.Error) Assert.assertEquals(resultExpected.data, it.data)
            }

            detailsViewModel.state.test {
                Assert.assertEquals(
                    CharacterDetailsState.Error(unknownErrorCode.toString()), expectMostRecentItem()
                )
            }
        }
    }
}