package com.seosh817.moviehub.core.model

data class MovieResponse(
    val page: Long,
    val results: List<Movie>,
    val totalPages: Long,
    val totalResults: Long,
)