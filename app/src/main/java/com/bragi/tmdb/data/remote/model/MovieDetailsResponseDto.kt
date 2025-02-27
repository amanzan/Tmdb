package com.bragi.tmdb.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponseDto(
    val id: Int,
    val budget: Int,
    val revenue: Int
)