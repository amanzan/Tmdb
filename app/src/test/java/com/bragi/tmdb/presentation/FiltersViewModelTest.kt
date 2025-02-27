package com.bragi.tmdb.presentation

import com.bragi.tmdb.domain.model.Genre
import com.bragi.tmdb.domain.usecase.GetGenresUseCase
import com.bragi.tmdb.presentation.filters.FiltersUiState
import com.bragi.tmdb.presentation.filters.FiltersViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FiltersViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var getGenresUseCase: GetGenresUseCase
    private lateinit var viewModel: FiltersViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getGenresUseCase = mockk(relaxed = true)
        viewModel = FiltersViewModel(getGenresUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadGenres success updates uiState to Success`() = runTest {
        // Arrange: Prepare a fake genres list
        val fakeGenres = listOf(
            Genre(id = 0, name = "All"),
            Genre(id = 1, name = "Action"),
            Genre(id = 2, name = "Comedy")
        )
        coEvery { getGenresUseCase() } returns fakeGenres

        // Act
        viewModel.loadGenres()
        advanceUntilIdle()

        // Assert: Verify uiState is Success and the use case was called
        assertTrue(viewModel.uiState.value is FiltersUiState.Success)
        coVerify { getGenresUseCase() }
    }

    @Test
    fun `loadGenres error updates uiState to Error`() = runTest {
        // Arrange: Make the use case throw an exception
        coEvery { getGenresUseCase() } throws Exception("Error loading genres")

        // Act
        viewModel.loadGenres()
        advanceUntilIdle()

        // Assert: Verify uiState is Error
        assertTrue(viewModel.uiState.value is FiltersUiState.Error)
    }
}
