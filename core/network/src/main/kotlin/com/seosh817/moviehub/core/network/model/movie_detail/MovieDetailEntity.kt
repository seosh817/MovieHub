package com.seosh817.moviehub.core.network.model.movie_detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailEntity(
    @SerialName("adult")
    val adult: Boolean?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollectionEntity?,
    @SerialName("budget")
    val budget: Long?,
    @SerialName("genres")
    val genreEntities: List<GenreEntity>?,
    @SerialName("homepage")
    val homepage: String?,
    @SerialName("id")
    val id: Long,
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("original_title")
    val originalTitle: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyEntity>?,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryEntity>?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("revenue")
    val revenue: Long?,
    @SerialName("runtime")
    val runtime: Long?,
    @SerialName("spoken_languages")
    val spokenLanguageEntities: List<SpokenLanguageEntity>?,
    @SerialName("status")
    val status: String?,
    @SerialName("tagline")
    val tagline: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("video")
    val video: Boolean?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Long?,
)
