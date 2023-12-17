package com.seosh817.ui.ktx

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

val <T : Any> LazyPagingItems<T>.isError: Boolean
    get() = loadState.refresh is LoadState.Error || loadState.append is LoadState.Error