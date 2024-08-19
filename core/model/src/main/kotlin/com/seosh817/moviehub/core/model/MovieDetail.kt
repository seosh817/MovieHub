package com.seosh817.moviehub.core.model

data class MovieDetail(
    val adult: Boolean?,
    val backdropPath: String?,
    val belongsToCollection: BelongsToCollection?,
    val budget: Long?,
    val genreEntities: List<Genre>?,
    val homepage: String?,
    val id: Long,
    val imdbId: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany>?,
    val productionCountries: List<ProductionCountry>?,
    val releaseDate: String?,
    val revenue: Long?,
    val runtime: Long?,
    val spokenLanguageEntities: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Long?,
)

data class BelongsToCollection(
    val id: Long,
    val name: String?,
    val posterPath: String?,
    val backdropPath: String?,
)

data class ProductionCompany(
    val id: Long,
    val logoPath: String?,
    val name: String?,
    val originCountry: String?,
)

data class ProductionCountry(
    val iso31661: String?,
    val name: String?,
)

data class SpokenLanguage(
    val englishName: String?,
    val iso6391: String?,
    val name: String?,
)

data class Genre(
    val id: Long,
    val name: String?,
)
