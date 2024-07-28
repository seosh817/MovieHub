package com.seosh817.moviehub.core.domain.usecase

import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface GetSearchUseCase {

    operator fun invoke(query: String, page: Int, language: String? = null, scope: CoroutineScope): Flow<List<UserMovie>>
}