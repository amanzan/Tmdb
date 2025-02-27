package com.bragi.tmdb.domain.repository

import com.bragi.tmdb.domain.model.Genre
import com.bragi.tmdb.domain.model.Movie

interface MovieRepository {
    suspend fun getGenres(): List<Genre>
    suspend fun getMovies(genreId: Int?): List<Movie>
}