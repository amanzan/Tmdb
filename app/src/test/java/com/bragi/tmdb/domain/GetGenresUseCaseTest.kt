package com.bragi.tmdb.domain

import com.bragi.tmdb.domain.model.Genre
import com.bragi.tmdb.domain.repository.MovieRepository
import com.bragi.tmdb.domain.usecase.GetGenresUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetGenresUseCaseTest {

    private val repository: MovieRepository = mockk()
    private val getGenresUseCase = GetGenresUseCase(repository)

    @Test
    fun `invoke returns genres list when repository succeeds`() = runTest {
        val fakeGenres = listOf(
            Genre(id = 0, name = "All"),
            Genre(id = 1, name = "Action"),
            Genre(id = 2, name = "Comedy")
        )
        coEvery { repository.getGenres() } returns fakeGenres

        val result = getGenresUseCase()

        assertEquals(fakeGenres, result)
        coVerify { repository.getGenres() }
    }

    @Test(expected = Exception::class)
    fun `invoke throws exception when repository fails`() = runTest {
        coEvery { repository.getGenres() } throws Exception("Error loading genres")

        getGenresUseCase()
    }
}
