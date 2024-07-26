package com.seosh817.moviehub.core.network.model.credits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCrew(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("gender")
    val gender: Long,
    @SerialName("id")
    val id: Long,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    @SerialName("name")
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String?,
    @SerialName("credit_id")
    val creditId: String,
    @SerialName("department")
    val department: String,
    @SerialName("job")
    val job: String,
)