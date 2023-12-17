package com.seosh817.ui.ktx

import coil.compose.AsyncImagePainter

val AsyncImagePainter.State.isErrorOrEmpty: Boolean
    get() = this is AsyncImagePainter.State.Error || this is AsyncImagePainter.State.Empty

val AsyncImagePainter.State.isLoading: Boolean
    get() = this is AsyncImagePainter.State.Loading