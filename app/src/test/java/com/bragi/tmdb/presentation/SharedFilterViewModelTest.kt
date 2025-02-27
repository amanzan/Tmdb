package com.bragi.tmdb.presentation

import com.bragi.tmdb.presentation.filters.SharedFilterViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class SharedFilterViewModelTest {
    @Test
    fun `selected genre state updates correctly`() {
        val viewModel = SharedFilterViewModel()
        viewModel.selectedGenreId = 1
        viewModel.selectedGenreName = "Action"
        assertEquals(1, viewModel.selectedGenreId)
        assertEquals("Action", viewModel.selectedGenreName)
    }
}
