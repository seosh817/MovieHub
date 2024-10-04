/*
 * Copyright 2024 seosh817 (Seunghwan Seo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seosh817.moviehub.core.domain.usecase

import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.fetchDataToFlow
import com.seosh817.moviehub.core.common.network.Dispatcher
import com.seosh817.moviehub.core.common.network.MovieHubDispatchers
import com.seosh817.moviehub.core.domain.repository.VideosRepository
import com.seosh817.moviehub.core.model.VideoResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(
    private val videosRepository: VideosRepository,
    @Dispatcher(MovieHubDispatchers.IO) private val dispatcher: CoroutineDispatcher,
) {

    operator fun invoke(movieId: Long): Flow<VideoResponse> =
        fetchDataToFlow {
            videosRepository
                .fetchVideos(movieId)
        }
            .catch {
                ResultState.Failure.Exception(it)
            }
            .flowOn(dispatcher)
}
