package com.seosh817.ui.ktx

import com.seosh817.ui.image.ImageSize

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/%s%s"

val String.formatProfileImageUrl: String
    get() = String.format(TMDB_IMAGE_BASE_URL, ImageSize.W185.size, this)

val String.formatPosterImageUrl: String
    get() = String.format(TMDB_IMAGE_BASE_URL, ImageSize.W780.size, this)

val String.formatBackdropImageUrl: String
    get() = String.format(TMDB_IMAGE_BASE_URL, ImageSize.W1280.size, this)