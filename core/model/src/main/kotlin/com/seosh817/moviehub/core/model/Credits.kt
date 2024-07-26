package com.seosh817.moviehub.core.model

data class Credits(
    val id: Long,
    val cast: List<Cast>?,
    val crew: List<Crew>?,
)

data class Cast(
    val adult: Boolean?,
    val gender: Long?,
    val id: Long,
    val knownForDepartment: String?,
    val name: String?,
    val originalName: String?,
    val popularity: Double?,
    val profilePath: String?,
    val castId: Long?,
    val character: String?,
    val creditId: String?,
    val order: Long?,
)

data class Crew(
    val adult: Boolean?,
    val gender: Long?,
    val id: Long,
    val knownForDepartment: String?,
    val name: String?,
    val originalName: String?,
    val popularity: Double?,
    val profilePath: String?,
    val creditId: String?,
    val department: String?,
    val job: String?,
)