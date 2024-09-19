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
package com.seosh817.ui.ktx

import com.seosh817.ui.image.ImageSize

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/%s%s"

val String.formatLogoImageUrl: String
    get() = String.format(TMDB_IMAGE_BASE_URL, ImageSize.W92.size, this)

val String.formatProfileImageUrl: String
    get() = String.format(TMDB_IMAGE_BASE_URL, ImageSize.W185.size, this)

val String.formatPosterImageUrl: String
    get() = String.format(TMDB_IMAGE_BASE_URL, ImageSize.W780.size, this)

val String.formatBackdropImageUrl: String
    get() = String.format(TMDB_IMAGE_BASE_URL, ImageSize.W1280.size, this)
