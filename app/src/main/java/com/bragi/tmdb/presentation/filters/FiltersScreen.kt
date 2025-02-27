package com.bragi.tmdb.presentation.filters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bragi.tmdb.domain.model.Genre
import com.bragi.tmdb.presentation.common.ErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(
    onFilterSelected: (Genre) -> Unit,
    onBack: () -> Unit,
    filtersViewModel: FiltersViewModel = hiltViewModel(),
    sharedFilterViewModel: SharedFilterViewModel = hiltViewModel()
) {
    val uiState by filtersViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        filtersViewModel.loadGenres()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Genre") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (uiState) {
                is FiltersUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is FiltersUiState.Error -> {
                    ErrorScreen(
                        message = (uiState as FiltersUiState.Error).message,
                        onRetry = { filtersViewModel.loadGenres() },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is FiltersUiState.Success -> {
                    val genres = (uiState as FiltersUiState.Success).genres
                    LazyColumn {
                        items(genres) { genre ->
                            GenreItem(
                                genre = genre,
                                isSelected = (genre.id == sharedFilterViewModel.selectedGenreId),
                                onClick = {
                                    sharedFilterViewModel.selectedGenreId = genre.id
                                    sharedFilterViewModel.selectedGenreName = genre.name
                                    onFilterSelected(genre)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
