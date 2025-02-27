package com.bragi.tmdb.domain.usecase

import com.bragi.tmdb.domain.model.Genre
import com.bragi.tmdb.domain.repository.MovieRepository

class GetGenresUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(): List<Genre> {
        return repository.getGenres()
    }
}