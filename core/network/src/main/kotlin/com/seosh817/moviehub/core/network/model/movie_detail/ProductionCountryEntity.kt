package com.seosh817.moviehub.core.network.model.movie_detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountryEntity(
    @SerialName("iso_3166_1")
    val iso31661: String?,
    @SerialName("name")
    val name: String?,
)
