package com.bragi.tmdb.data.remote.service

import com.bragi.tmdb.data.remote.model.GenreResponseDto
import com.bragi.tmdb.data.remote.model.MovieDetailsResponseDto
import com.bragi.tmdb.data.remote.model.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {
    @GET("genre/movie/list")
    suspend fun getMovieGenres(): GenreResponseDto

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("with_genres") withGenres: String? = null
    ): MoviesResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetailsResponseDto
}