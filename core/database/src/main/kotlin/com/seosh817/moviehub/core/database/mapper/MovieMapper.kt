package com.seosh817.moviehub.core.database.mapper

import com.seosh817.moviehub.core.database.model.MovieEntity
import com.seosh817.moviehub.core.model.MovieOverview

fun MovieEntity.asExternalModel() = MovieOverview(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genreIds,
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