package com.bragi.tmdb.domain

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

import com.bragi.tmdb.domain.model.Movie
import com.bragi.tmdb.domain.repository.MovieRepository
import com.bragi.tmdb.domain.usecase.GetMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class GetMoviesUseCaseTest {

    private val repository: MovieRepository = mockk()
    private val getMoviesUseCase = GetMoviesUseCase(repository)

    @Test
    fun `invoke returns movies list when repository succeeds`() = runTest {
        val fakeMovies = listOf(
            Movie(id = 1, title = "Movie 1", posterPath = "/path1.jpg", rating = 8.0, budget = 1000000, revenue = 5000000),
            Movie(id = 2, title = "Movie 2", posterPath = "/path2.jpg", rating = 7.5, budget = 2000000, revenue = 6000000)
        )
        coEvery { repository.getMovies(null) } returns fakeMovies

        val result = getMoviesUseCase(null)

        assertEquals(fakeMovies, result)
        coVerify { repository.getMovies(null) }
    }

    @Test(expected = Exception::class)
    fun `invoke throws exception when repository fails`() = runTest {
        coEvery { repository.getMovies(null) } throws Exception("Error loading movies")

        getMoviesUseCase(null)
    }
}
