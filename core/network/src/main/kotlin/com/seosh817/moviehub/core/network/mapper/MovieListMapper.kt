package com.seosh817.moviehub.core.network.mapper

import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.model.MoviesResponse
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMovieOverview
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse

fun MoviesResponse.asEntity() = NetworkMoviesResponse(
    page = page,
    results = results.map { it.asEntity() },
    totalPages = totalPages,
    totalResults = totalResults,
)

fun NetworkMoviesResponse.asExternalModel() = MoviesResponse(
    page = page,
    results = results.map { it.asExternalModel() },
    totalPages = totalPages,
    totalResults = totalResults,
)

fun MovieOverview.asEntity() = NetworkMovieOverview(
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

fun NetworkMovieOverview.asExternalModel() = MovieOverview(
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