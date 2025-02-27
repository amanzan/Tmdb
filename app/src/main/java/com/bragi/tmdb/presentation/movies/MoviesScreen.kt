package com.bragi.tmdb.presentation.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bragi.tmdb.domain.model.Movie
import com.bragi.tmdb.presentation.filters.SharedFilterViewModel

@Composable
fun MoviesScreen(
    onNavigateToFilters: () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel(),
    sharedFilterViewModel: SharedFilterViewModel = hiltViewModel(),
    navController: NavController
) {
    // Listen for filter changes using the shared ViewModel
    LaunchedEffect(sharedFilterViewModel.selectedGenreId) {
        viewModel.selectedGenreId = sharedFilterViewModel.selectedGenreId.takeIf { it != 0 }
        viewModel.loadMovies()
    }

    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
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

        FloatingActionButton(
            onClick = onNavigateToFilters,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Text("Filters")
        }
    }
}


@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            movie.posterPath?.let { path ->
                val imageUrl = "https://image.tmdb.org/t/p/w500$path"
                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Rating: ${movie.rating}")
            Text(text = "Budget: \$${movie.budget}")
            Text(text = "Revenue: \$${movie.revenue}")
        }
    }
}
