package com.bragi.tmdb.data.remote.model

import com.squareup.moshi.Json

data class MovieDto(
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "vote_average")
    val rating: Double
)