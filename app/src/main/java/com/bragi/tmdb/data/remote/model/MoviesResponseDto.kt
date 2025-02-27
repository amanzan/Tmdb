package com.bragi.tmdb.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponseDto(
    val results: List<MovieDto>
)