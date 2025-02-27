package com.bragi.tmdb.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val rating: Double,
    val budget: Int,
    val revenue: Int
)