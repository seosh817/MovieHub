package com.seosh817.moviehub.core.domain.usecase

import androidx.paging.PagingData
import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.domain.repository.UserSearchRepository
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSearchMoviesUseCase @Inject constructor(
  private val searchMoviesRepository: UserSearchRepository,
  @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher
): GetSearchUseCase {

  override operator fun invoke(query: String, language: String?, scope: CoroutineScope): Flow<PagingData<UserMovie>> = searchMoviesRepository
    .searchMovies(query, language, scope)
    .flowOn(dispatcher)
}
