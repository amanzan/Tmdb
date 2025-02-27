package com.bragi.tmdb.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.bragi.tmdb.presentation.navigation.AppNavHost
import com.bragi.tmdb.presentation.theme.TmdbTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TmdbTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}
