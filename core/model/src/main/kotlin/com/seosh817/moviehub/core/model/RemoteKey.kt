package com.seosh817.moviehub.core.model

data class RemoteKey(
    val type: MovieType,
    val movieId: Long,
    val prevKey: Long?,
    val nextKey: Long?,
)
