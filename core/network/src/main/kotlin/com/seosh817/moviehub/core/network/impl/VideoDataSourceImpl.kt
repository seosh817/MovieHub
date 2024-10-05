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
package com.seosh817.moviehub.core.network.impl

import com.seosh817.common.network.handleApi
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.network.model.video.NetworkVideoResponse
import com.seosh817.moviehub.core.network.service.video.VideoService
import com.seosh817.moviehub.core.network.source.VideosRemoteDataSource
import javax.inject.Inject

class VideoDataSourceImpl @Inject constructor(
    private val videoService: VideoService,
) : VideosRemoteDataSource {

    override suspend fun fetchVideos(movieId: Long): ResultState<NetworkVideoResponse> {
        return handleApi {
            videoService.fetchVideos(movieId)
        }
    }
}
