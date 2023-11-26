package com.seosh817.moviehub.core.network.model.movie_list

import kotlinx.serialization.SerialName

data class DatesEntity(
    @SerialName("maximum")
    val maximum: String,
    @SerialName("minimum")
    val minimum: String,
)