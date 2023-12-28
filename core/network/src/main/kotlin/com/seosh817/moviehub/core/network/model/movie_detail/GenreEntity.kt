package com.seosh817.moviehub.core.network.model.movie_detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
)
