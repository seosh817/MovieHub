package com.seosh817.moviehub.core.network.mapper

import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.model.MoviesResponse
import com.seosh817.moviehub.core.network.model.movie_list.MovieOverviewEntity
import com.seosh817.moviehub.core.network.model.movie_list.MoviesResponseEntity

fun MoviesResponse.asEntity() = MoviesResponseEntity(
    page = page,
    results = results.map { it.asEntity() },
    totalPages = totalPages,
    totalResults = totalResults,
)

fun MoviesResponseEntity.asExternalModel() = MoviesResponse(
    page = page,
    results = results.map { it.asExternalModel() },
    totalPages = totalPages,
    totalResults = totalResults,
)

fun MovieOverview.asEntity() = MovieOverviewEntity(
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genreIds,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)

fun MovieOverviewEntity.asExternalModel() = MovieOverview(
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genreIds,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)