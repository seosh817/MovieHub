package com.seosh817.moviehub.core.data.model

import com.seosh817.moviehub.core.database.model.MovieEntity
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMovieOverview

fun NetworkMovieOverview.asEntity() = MovieEntity(
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
