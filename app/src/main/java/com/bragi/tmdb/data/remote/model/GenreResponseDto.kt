package com.bragi.tmdb.data.remote.model

import com.squareup.moshi.Json

data class GenreResponseDto(
    @Json(name = "genres")
    val genres: List<GenreDto>
)