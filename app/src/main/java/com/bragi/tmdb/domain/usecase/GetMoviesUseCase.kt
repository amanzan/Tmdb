package com.bragi.tmdb.domain.usecase

import com.bragi.tmdb.domain.model.Movie
import com.bragi.tmdb.domain.repository.MovieRepository

class GetMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(genreId: Int?): List<Movie> {
        return repository.getMovies(genreId)
    }
}