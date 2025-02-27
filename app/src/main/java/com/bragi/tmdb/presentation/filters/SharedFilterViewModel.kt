package com.bragi.tmdb.presentation.filters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedFilterViewModel @Inject constructor() : ViewModel() {
    // Default to "All" (using 0 as the id for "All")
    var selectedGenreId by mutableStateOf(0)
}
