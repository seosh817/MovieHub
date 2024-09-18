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
