package com.seosh817.moviehub.core.network.model.credits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("cast")
    val castEntity: List<CastEntity>,
    @SerialName("crew")
    val crewEntity: List<CrewEntity>,
)
