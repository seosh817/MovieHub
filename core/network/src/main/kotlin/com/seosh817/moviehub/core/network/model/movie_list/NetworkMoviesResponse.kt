package com.seosh817.moviehub.core.network.model.movie_list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMoviesResponse(
    @SerialName("page")
    val page: Long?,
    @SerialName("results")
    val results: List<NetworkMovieOverview>,
    @SerialName("total_pages")
    val totalPages: Long?,
    @SerialName("total_results")
    val totalResults: Long?,
)
