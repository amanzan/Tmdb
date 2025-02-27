package com.bragi.tmdb.data.repository

import android.util.Log
import com.bragi.tmdb.data.remote.service.TmdbApiService
import com.bragi.tmdb.domain.model.Genre
import com.bragi.tmdb.domain.model.Movie
import com.bragi.tmdb.domain.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withTimeout

class MovieRepositoryImpl(
    private val apiService: TmdbApiService
) : MovieRepository {

    private suspend fun <T> retryWithTimeout(
        times: Int = 3,
        timeoutMillis: Long = 30000,
        block: suspend () -> T
    ): T {
        var lastException: Exception? = null
        repeat(times) {
            try {
                return withTimeout(timeoutMillis) { block() }
            } catch (e: Exception) {
                lastException = e
            }
        }
        throw lastException ?: Exception("Unknown error")
    }

    override suspend fun getGenres(): List<Genre> {
        val response = retryWithTimeout { apiService.getMovieGenres() }
        return response.genres.map { Genre(it.id, it.name) }
    }

    override suspend fun getMovies(genreId: Int?): List<Movie> {
        val genreParam = if (genreId != null && genreId != 0) genreId.toString() else null
        val moviesResponse = retryWithTimeout { apiService.discoverMovies(withGenres = genreParam) }
        return coroutineScope {
            moviesResponse.results.map { movieDto ->
                async {
                    val details = try {
                        retryWithTimeout { apiService.getMovieDetails(movieDto.id) }
                    } catch (e: Exception) {
                        null
                    }
                    Movie(
                        id = movieDto.id,
                        title = movieDto.title,
                        posterPath = movieDto.posterPath,
                        rating = movieDto.rating,
                        budget = details?.budget ?: 0,
                        revenue = details?.revenue ?: 0
                    )
                }
            }.map { it.await() }
        }
    }
}
