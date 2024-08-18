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

    constructor(movieDetail: MovieDetail, isBookmarked: Boolean) : this(
        adult = movieDetail.adult,
        backdropPath = movieDetail.backdropPath,
        genreIds = movieDetail.genreEntities?.map { it.id },
        id = movieDetail.id,
        originalLanguage = movieDetail.originalLanguage,
        originalTitle = movieDetail.originalTitle,
        overview = movieDetail.overview,
        popularity = movieDetail.popularity,
        posterPath = movieDetail.posterPath,
        releaseDate = movieDetail.releaseDate,
        title = movieDetail.title,
        video = movieDetail.video,
        voteAverage = movieDetail.voteAverage,
        voteCount = movieDetail.voteCount,
        isBookmarked = isBookmarked
    )

    constructor(movieOverview: MovieOverview, isBookmarked: Boolean) : this(
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
        isBookmarked = isBookmarked
    )

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

    fun toMovieOverView(): MovieOverview {
        return MovieOverview(
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
    }
}
