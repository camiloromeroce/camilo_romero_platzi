package com.example.weather.presentation.detail_viewmodel

import app.cash.turbine.test
import com.example.weather.CoroutinesTestRule
import com.example.weather.mocks.characterItemDetailSectionMock
import com.example.weather.mocks.sectionMock
import com.example.weather.presentation.presentation.detail_section.DetailSectionViewModel
import com.example.weather.presentation.presentation.detail_section.state.CharacterDetailsSectionState
import com.example.domain.home.common.Resource
import com.example.domain.home.common.notDetailsErrorCode
import com.example.domain.home.common.unknownErrorCode
import com.example.domain.model.detail_section.CharacterDetailSectionItem
import com.example.usecases.s.detail_section.GetCharacterDetailSectionUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailsSectionViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var detailsSectionViewModel: DetailSectionViewModel

    @Mock
    private lateinit var getSectionDetailsUseCase: GetCharacterDetailSectionUseCase

    @Before
    fun setUp() {
        detailsSectionViewModel = DetailSectionViewModel(getSectionDetailsUseCase)
    }

    @Test
    fun `getCharacterSectionDetail listen Flows emits a list of characters and change the UI state to LoadSectionDetails`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Success(
                listOf(
                    characterItemDetailSectionMock,
                    characterItemDetailSectionMock.copy(id = "54321")
                )
            )

            whenever(
                getSectionDetailsUseCase.invoke(
                    sectionMock, characterItemDetailSectionMock.id
                )
            ).thenReturn(
                flowOf(resultExpected)
            )

            detailsSectionViewModel.getCharacterDetailSection(
                sectionMock, characterItemDetailSectionMock.id
            )

            verify(getSectionDetailsUseCase).invoke(sectionMock, characterItemDetailSectionMock.id)

            getSectionDetailsUseCase(sectionMock, characterItemDetailSectionMock.id).collect {
                if (it is Resource.Success) Assert.assertEquals(resultExpected.data, it.data)
            }

            detailsSectionViewModel.state.test {
                Assert.assertEquals(
                    CharacterDetailsSectionState.LoadSectionDetails(resultExpected.data!!),
                    expectMostRecentItem()
                )
            }
        }
    }

    @Test
    fun `getCharacterSectionDetail listen Flows emits a emptyList and change the UI state to Error`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Success(emptyList<CharacterDetailSectionItem>())

            whenever(
                getSectionDetailsUseCase.invoke(
                    sectionMock, characterItemDetailSectionMock.id
                )
            ).thenReturn(
                flowOf(resultExpected)
            )

            detailsSectionViewModel.getCharacterDetailSection(
                sectionMock, characterItemDetailSectionMock.id
            )

            verify(getSectionDetailsUseCase).invoke(sectionMock, characterItemDetailSectionMock.id)

            getSectionDetailsUseCase(sectionMock, characterItemDetailSectionMock.id).collect {
                if (it is Resource.Error) Assert.assertEquals(resultExpected.data, it.data)
            }

            detailsSectionViewModel.state.test {
                Assert.assertEquals(
                    CharacterDetailsSectionState.Error(notDetailsErrorCode.toString()),
                    expectMostRecentItem()
                )
            }
        }
    }

    @Test
    fun `getCharacterDetail listen Flows emits a null and change the UI state to Error`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected =
                Resource.Error<List<CharacterDetailSectionItem>>(unknownErrorCode.toString(), null)

            whenever(
                getSectionDetailsUseCase.invoke(
                    sectionMock, characterItemDetailSectionMock.id
                )
            ).thenReturn(
                flowOf(resultExpected)
            )

            detailsSectionViewModel.getCharacterDetailSection(
                sectionMock, characterItemDetailSectionMock.id
            )

            verify(getSectionDetailsUseCase).invoke(sectionMock, characterItemDetailSectionMock.id)

            getSectionDetailsUseCase(sectionMock, characterItemDetailSectionMock.id).collect {
                if (it is Resource.Error) Assert.assertEquals(resultExpected.data, it.data)
            }

            detailsSectionViewModel.state.test {
                Assert.assertEquals(
                    CharacterDetailsSectionState.Error(unknownErrorCode.toString()),
                    expectMostRecentItem()
                )
            }
        }
    }

}