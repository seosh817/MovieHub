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
package com.seosh817.moviehub.feature.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.asResult
import com.seosh817.moviehub.core.domain.usecase.GetFavoritesUseCase
import com.seosh817.moviehub.core.domain.usecase.PostBookmarkUseCase
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.model.UserMovie
import com.seosh817.moviehub.core.model.state.PostBookmarkUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val postBookmarkUseCase: PostBookmarkUseCase,
    getFavoritesUseCase: GetFavoritesUseCase,
) : ViewModel() {

    val pagingMoviesStateFlow: Flow<PagingData<UserMovie>> =
        getFavoritesUseCase
            .invoke(scope = viewModelScope)
            .onStart {
                _isRefreshing.emit(true)
            }
            .distinctUntilChanged()
            .catch {
                _isRefreshing.emit(false)
            }
            .onEach {
                _isRefreshing.emit(false)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = PagingData.empty(),
            )

    private val _postBookmarkUiState: MutableStateFlow<PostBookmarkUiState> = MutableStateFlow(PostBookmarkUiState.Success)
    val postBookmarkUiState: StateFlow<PostBookmarkUiState> = _postBookmarkUiState.asStateFlow()

    private val _bookmarksUiEvent = MutableSharedFlow<BookmarksUiEvent>()
    val bookmarksUiEvent: Flow<BookmarksUiEvent> = _bookmarksUiEvent.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun onBookmarkClick(userMovie: UserMovie) = postBookmarkUseCase
        .invoke(MovieType.POPULAR, userMovie)
        .asResult()
        .onStart {
            _postBookmarkUiState.emit(PostBookmarkUiState.Loading)
        }
        .onEach {
            when (it) {
                is ResultState.Success -> {
                    _bookmarksUiEvent.emit(BookmarksUiEvent.ShowBookmarkedMessage(userMovie.isBookmarked, userMovie.id))
                    _postBookmarkUiState.emit(PostBookmarkUiState.Success)
                }

                is ResultState.Failure<*> -> {
                    _postBookmarkUiState.emit(PostBookmarkUiState.Error(it.e))
                }
            }
        }
        .launchIn(viewModelScope)
}
