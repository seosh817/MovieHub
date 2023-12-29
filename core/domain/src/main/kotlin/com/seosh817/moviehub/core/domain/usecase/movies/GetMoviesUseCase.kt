package com.seosh817.moviehub.core.domain.usecase.movies

import androidx.paging.PagingData
import com.seosh817.moviehub.core.model.MovieOverview
import kotlinx.coroutines.flow.Flow

interface GetMoviesUseCase {

    operator fun invoke(language: String? = null): Flow<PagingData<MovieOverview>>
}
