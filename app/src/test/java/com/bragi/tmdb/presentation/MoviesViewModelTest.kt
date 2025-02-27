package com.bragi.tmdb.presentation

import com.bragi.tmdb.domain.model.Movie
import com.bragi.tmdb.domain.usecase.GetMoviesUseCase
import com.bragi.tmdb.presentation.movies.MovieUiState
import com.bragi.tmdb.presentation.movies.MoviesViewModel
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
class MoviesViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var getMoviesUseCase: GetMoviesUseCase
    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setup() {
        // Set main dispatcher for testing
        Dispatchers.setMain(testDispatcher)

        // Create a mock use case
        getMoviesUseCase = mockk(relaxed = true)
        viewModel = MoviesViewModel(getMoviesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadMovies success updates uiState to Success`() = runTest {
        // Arrange: Prepare a fake movies list
        val fakeMovies = listOf(
            Movie(
                id = 1,
                title = "Test Movie",
                posterPath = "/path.jpg",
                rating = 8.0,
                budget = 1000000,
                revenue = 5000000
            )
        )
        coEvery { getMoviesUseCase(null) } returns fakeMovies

        // Act: Set genre filter to null ("All") and load movies
        viewModel.selectedGenreId = null
        viewModel.loadMovies()
        advanceUntilIdle()

        // Assert: Verify uiState is Success and the use case was called
        assertTrue(viewModel.uiState.value is MovieUiState.Success)
        coVerify { getMoviesUseCase(null) }
    }

    @Test
    fun `loadMovies error updates uiState to Error`() = runTest {
        // Arrange: Make the use case throw an exception
        coEvery { getMoviesUseCase(any()) } throws Exception("Error loading movies")

        // Act
        viewModel.loadMovies()
        advanceUntilIdle()

        // Assert: Verify uiState is Error
        assertTrue(viewModel.uiState.value is MovieUiState.Error)
    }
}
