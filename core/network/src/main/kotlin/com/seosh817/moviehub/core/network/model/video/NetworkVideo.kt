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
package com.seosh817.moviehub.core.network.model.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkVideo(

    @SerialName("id") val id: String,
    @SerialName("iso_639_1") val iso6391: String,
    @SerialName("iso_3166_1") val iso31661: String,
    @SerialName("name") val name: String,
    @SerialName("key") val key: String,
    @SerialName("site") val site: String,
    @SerialName("size") val size: Int,
    @SerialName("type") val type: String,
    @SerialName("official") val official: Boolean,
    @SerialName("published_at") val publishedAt: String,
)
