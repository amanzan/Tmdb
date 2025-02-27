package com.bragi.tmdb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bragi.tmdb.domain.model.Genre
import com.bragi.tmdb.presentation.filters.FiltersScreen
import com.bragi.tmdb.presentation.filters.SharedFilterViewModel
import com.bragi.tmdb.presentation.movies.MoviesScreen
import com.bragi.tmdb.presentation.movies.MoviesViewModel

sealed class Screen(val route: String) {
    object Movies : Screen("movies")
    object Filters : Screen("filters")
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        // Create a nested graph with route "home" to serve as the common owner.
        navigation(startDestination = Screen.Movies.route, route = "home") {
            composable(Screen.Movies.route) {
                val parentEntry = navController.getBackStackEntry("home")
                val sharedFilterViewModel = hiltViewModel<SharedFilterViewModel>(parentEntry)
                MoviesScreen(
                    navController = navController,
                    onNavigateToFilters = { navController.navigate(Screen.Filters.route) },
                    sharedFilterViewModel = sharedFilterViewModel
                )
            }
            composable(Screen.Filters.route) {
                val parentEntry = navController.getBackStackEntry("home")
                val sharedFilterViewModel = hiltViewModel<SharedFilterViewModel>(parentEntry)
                FiltersScreen(
                    onFilterSelected = { genre ->
                        // Update the shared filter state
                        sharedFilterViewModel.selectedGenreId = genre.id
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() },
                    sharedFilterViewModel = sharedFilterViewModel
                )
            }
        }
    }
}


