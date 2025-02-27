package com.bragi.tmdb.domain.usecase

import com.bragi.tmdb.domain.model.Genre
import com.bragi.tmdb.domain.repository.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(): List<Genre> {
        return repository.getGenres()
    }
}