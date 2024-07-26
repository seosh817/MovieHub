package com.seosh817.moviehub.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seosh817.moviehub.core.model.MovieOverview

@Entity(
    tableName = "favorites"
)
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    val adult: Boolean?,
    @ColumnInfo(name = "back_drop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Long>?,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String?,
    @ColumnInfo(name = "original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,
    @ColumnInfo(name = "vote_count")
    val voteCount: Long?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)

fun FavoriteEntity.asExternalModel() = MovieOverview(
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

fun MovieOverview.asFavoriteEntity() = FavoriteEntity(
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