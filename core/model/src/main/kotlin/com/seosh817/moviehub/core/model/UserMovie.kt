/*
 * Copyright 2024 seosh817 (Seunghwan Seo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    val isBookmarked: Boolean,
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
        isBookmarked = isBookmarked,
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
        isBookmarked = isBookmarked,
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
        isBookmarked = userSettings.bookmarkedMovieIds.contains(movieOverview.id),
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
