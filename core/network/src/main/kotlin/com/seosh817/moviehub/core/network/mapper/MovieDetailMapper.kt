package com.seosh817.moviehub.core.network.mapper

import com.seosh817.moviehub.core.model.BelongsToCollection
import com.seosh817.moviehub.core.model.Genre
import com.seosh817.moviehub.core.model.MovieDetail
import com.seosh817.moviehub.core.model.ProductionCompany
import com.seosh817.moviehub.core.model.ProductionCountry
import com.seosh817.moviehub.core.model.SpokenLanguage
import com.seosh817.moviehub.core.network.model.movie_detail.BelongsToCollectionEntity
import com.seosh817.moviehub.core.network.model.movie_detail.GenreEntity
import com.seosh817.moviehub.core.network.model.movie_detail.MovieDetailEntity
import com.seosh817.moviehub.core.network.model.movie_detail.ProductionCompanyEntity
import com.seosh817.moviehub.core.network.model.movie_detail.ProductionCountryEntity
import com.seosh817.moviehub.core.network.model.movie_detail.SpokenLanguageEntity

fun MovieDetailEntity.asExternalModel() = MovieDetail(
    adult = adult,
    backdropPath = backdropPath,
    belongsToCollection = belongsToCollection?.asExternalModel(),
    budget = budget,
    genreEntities = genreEntities?.map { it.asExternalModel() },
    homepage = homepage,
    id = id,
    imdbId = imdbId,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    productionCompanies = productionCompanies?.map { it.asExternalModel() },
    productionCountries = productionCountries?.map { it.asExternalModel() },
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    spokenLanguageEntities = spokenLanguageEntities?.map { it.asExternalModel() },
    status = status,
    tagline = tagline,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)

fun BelongsToCollectionEntity.asExternalModel() = BelongsToCollection(
    backdropPath = backdropPath,
    id = id,
    name = name,
    posterPath = posterPath
)

fun GenreEntity.asExternalModel() = Genre(
    id = id,
    name = name,
)

fun ProductionCompanyEntity.asExternalModel() = ProductionCompany(
    id = id,
    logoPath = logoPath,
    name = name,
    originCountry = originCountry,
)

fun ProductionCountryEntity.asExternalModel() = ProductionCountry(
    iso31661 = iso31661,
    name = name,
)

fun SpokenLanguageEntity.asExternalModel() = SpokenLanguage(
    englishName = englishName,
    iso6391 = iso6391,
    name = name,
)