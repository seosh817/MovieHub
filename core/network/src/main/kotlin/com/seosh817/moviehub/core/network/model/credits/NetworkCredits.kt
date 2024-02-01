package com.seosh817.moviehub.core.network.model.credits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCredits(
    @SerialName("id")
    val id: Long,
    @SerialName("cast")
    val networkCast: List<NetworkCast>,
    @SerialName("crew")
    val networkCrew: List<NetworkCrew>,
)
