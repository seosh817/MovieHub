package com.seosh817.moviehub.core.network.mapper

import com.seosh817.moviehub.core.model.Movie
import com.seosh817.moviehub.core.model.MovieResponse
import com.seosh817.moviehub.core.network.model.movie_list.MovieEntity
import com.seosh817.moviehub.core.network.model.movie_list.MovieResponseEntity

fun MovieResponse.asEntity() = MovieResponseEntity(
    page = page,
    results = results.map { it.asEntity() },
    totalPages = totalPages,
    totalResults = totalResults,
)

fun MovieResponseEntity.asExternalModel() = MovieResponse(
    page = page,
    results = results.map { it.asExternalModel() },
    totalPages = totalPages,
    totalResults = totalResults,
)

fun Movie.asEntity() = MovieEntity(
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

fun MovieEntity.asExternalModel() = Movie(
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