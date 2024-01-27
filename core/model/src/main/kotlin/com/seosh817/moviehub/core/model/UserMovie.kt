package com.seosh817.moviehub.core.model

class UserMovie(
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
    val isBookmarked: Boolean
) {
    constructor(movieOverview: MovieOverview, userSettings: UserSettings) : this(
        adult = movieOverview.adult,
        backdropPath = movieOverview.backdropPath,
        genreIds = movieOverview.genreIds,
        id = movieOverview.id,
        originalLanguage = movieOverview.originalLanguage,
        originalTitle = movieOverview.originalTitle,
        overview = movieOverview.overview,
        popularity = movieOverview.popularity,
        posterPath = movieOverview.posterPath,
        releaseDate = movieOverview.releaseDate,
        title = movieOverview.title,
        video = movieOverview.video,
        voteAverage = movieOverview.voteAverage,
        voteCount = movieOverview.voteCount,
        isBookmarked = userSettings.bookmarkedMovieIds.contains(movieOverview.id)
    )
}
