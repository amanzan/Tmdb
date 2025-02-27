package com.bragi.tmdb.data.remote.model

import com.squareup.moshi.Json

data class MoviesResponseDto(
    @Json(name = "results")
    val results: List<MovieDto>
)