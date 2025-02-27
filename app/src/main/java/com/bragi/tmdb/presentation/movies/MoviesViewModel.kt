package com.bragi.tmdb.presentation.movies


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bragi.tmdb.domain.model.Movie
import com.bragi.tmdb.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MovieUiState {
    object Loading : MovieUiState()
    data class Success(val movies: List<Movie>) : MovieUiState()
    data class Error(val message: String) : MovieUiState()
}

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    var selectedGenreId: Int? = null

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = MovieUiState.Loading
            try {
                val movies = getMoviesUseCase(selectedGenreId)
                _uiState.value = MovieUiState.Success(movies)
            } catch (e: Exception) {
                Log.e("MoviesViewModel", "Error loading movies", e)
                _uiState.value = MovieUiState.Error("Error loading movies. Please check your connection.")
            }
        }
    }
}

