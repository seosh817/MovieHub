package com.seosh817.moviehub.core.network.model.movie_list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseEntity(
    @SerialName("page")
    val page: Long?,
    @SerialName("results")
    val results: List<MovieEntity>,
    @SerialName("total_pages")
    val totalPages: Long?,
    @SerialName("total_results")
    val totalResults: Long?,
)
