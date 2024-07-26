package com.seosh817.moviehub.core.model

data class MoviesResponse(
    val page: Long?,
    val results: List<MovieOverview>,
    val totalPages: Long?,
    val totalResults: Long?,
)