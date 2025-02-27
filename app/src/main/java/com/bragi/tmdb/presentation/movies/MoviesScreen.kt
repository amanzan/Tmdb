package com.bragi.tmdb.presentation.movies

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bragi.tmdb.presentation.filters.SharedFilterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    onNavigateToFilters: () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel(),
    sharedFilterViewModel: SharedFilterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentGenreName = sharedFilterViewModel.selectedGenreName

    LaunchedEffect(sharedFilterViewModel.selectedGenreId) {
        viewModel.selectedGenreId = sharedFilterViewModel.selectedGenreId.takeIf { it != 0 }
        viewModel.loadMovies()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movies - $currentGenreName") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToFilters) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Filters"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is MovieUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is MovieUiState.Error -> {
                    Text(
                        text = (uiState as MovieUiState.Error).message,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is MovieUiState.Success -> {
                    val movies = (uiState as MovieUiState.Success).movies
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(150.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(movies) { movie ->
                            MovieItem(movie = movie)
                        }
                    }
                }
            }
        }
    }
}
