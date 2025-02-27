package com.bragi.tmdb.presentation.filters

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bragi.tmdb.domain.model.Genre

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
        when (uiState) {
            is FiltersUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is FiltersUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = (uiState as FiltersUiState.Error).message)
                }
            }
            is FiltersUiState.Success -> {
                val genres = (uiState as FiltersUiState.Success).genres
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(genres) { genre ->
                        GenreItem(
                            genre = genre,
                            isSelected = (genre.id == sharedFilterViewModel.selectedGenreId),
                            onClick = {
                                // Update the shared selected genre
                                sharedFilterViewModel.selectedGenreId = genre.id
                                onFilterSelected(genre)
                            }
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun GenreItem(genre: Genre, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.LightGray else Color.White
        )
    ) {
        Text(
            text = genre.name,
            modifier = Modifier.padding(16.dp)
        )
    }
}


