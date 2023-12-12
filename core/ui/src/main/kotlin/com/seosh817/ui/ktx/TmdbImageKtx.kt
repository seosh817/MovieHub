package com.seosh817.ui.ktx

import com.seosh817.ui.image.BackdropSize

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/%s%s"

val String.formatBackdropImageUrl: String
    get() = String.format(TMDB_IMAGE_BASE_URL, BackdropSize.W1280.size, this)