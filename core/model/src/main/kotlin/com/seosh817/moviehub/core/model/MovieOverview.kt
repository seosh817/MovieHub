package com.seosh817.moviehub.core.model

data class MovieOverview(
    val adult: Boolean?,
    val backdropPath: String?,
    val genreIds: List<Long>?,
    val id: Long,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Long?,
)
