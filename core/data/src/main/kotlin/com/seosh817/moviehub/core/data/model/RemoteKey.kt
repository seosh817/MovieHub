package com.seosh817.moviehub.core.data.model

import com.seosh817.moviehub.core.database.model.RemoteKeyEntity
import com.seosh817.moviehub.core.model.RemoteKey

fun RemoteKey.asEntity() = RemoteKeyEntity(
    type = type,
    movieId = movieId,
    prevKey = prevKey,
    nextKey = nextKey,
)