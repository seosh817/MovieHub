package com.seosh817.moviehub.core.domain.usecase

import androidx.paging.PagingData
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface GetSearchUseCase {

    operator fun invoke(query: String, language: String? = null, scope: CoroutineScope): Flow<PagingData<UserMovie>>
}