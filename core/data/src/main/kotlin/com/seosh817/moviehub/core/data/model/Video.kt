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
package com.seosh817.moviehub.core.data.model

import com.seosh817.moviehub.core.model.Video
import com.seosh817.moviehub.core.model.VideoResponse
import com.seosh817.moviehub.core.network.model.video.NetworkVideo
import com.seosh817.moviehub.core.network.model.video.NetworkVideoResponse

fun NetworkVideoResponse.asExternalModel() = VideoResponse(
    id = id,
    videos = videos.map { it.asExternalModel() },
)

fun NetworkVideo.asExternalModel() = Video(
    id = id,
    iso6391 = iso6391,
    iso31661 = iso31661,
    name = name,
    key = key,
    site = site,
    size = size,
    type = type,
    official = official,
    publishedAt = publishedAt,
)
