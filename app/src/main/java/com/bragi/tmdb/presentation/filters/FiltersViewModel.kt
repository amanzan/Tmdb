package com.bragi.tmdb.presentation.filters

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bragi.tmdb.domain.model.Genre
import com.bragi.tmdb.domain.usecase.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FiltersUiState {
    object Loading : FiltersUiState()
    data class Success(val genres: List<Genre>) : FiltersUiState()
    data class Error(val message: String) : FiltersUiState()
}

@HiltViewModel
class FiltersViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FiltersUiState>(FiltersUiState.Loading)
    val uiState: StateFlow<FiltersUiState> = _uiState.asStateFlow()

    fun loadGenres() {
        viewModelScope.launch {
            _uiState.value = FiltersUiState.Loading
            try {
                // Prepend an "All" option (id 0) to the list of genres
                val genresList = listOf(Genre(0, "All")) + getGenresUseCase()
                _uiState.value = FiltersUiState.Success(genresList)
            } catch (e: Exception) {
                // Optionally log the error: Log.e("FiltersViewModel", "Error loading filters", e)
                _uiState.value = FiltersUiState.Error("Error loading filters. Please check your connection.")
            }
        }
    }
}

