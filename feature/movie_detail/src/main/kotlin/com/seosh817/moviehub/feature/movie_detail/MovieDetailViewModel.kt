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
package com.seosh817.moviehub.feature.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.asResult
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.usecase.GetCreditsUseCase
import com.seosh817.moviehub.core.domain.usecase.GetMovieDetailUseCase
import com.seosh817.moviehub.core.domain.usecase.GetVideosUseCase
import com.seosh817.moviehub.core.domain.usecase.PostBookmarkUseCase
import com.seosh817.moviehub.core.model.MovieDetail
import com.seosh817.moviehub.core.model.MovieDetailResult
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.model.UserMovie
import com.seosh817.moviehub.core.model.state.PostBookmarkUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val bookmarkUseCase: PostBookmarkUseCase,
    appPreferencesSettingsRepository: AppPreferencesRepository,
    savedStateHandle: SavedStateHandle,
    getMovieDetailUseCase: GetMovieDetailUseCase,
    getCreditsUseCase: GetCreditsUseCase,
    getVideosUseCase: GetVideosUseCase,
) : ViewModel() {

    private val movieType: MovieType = MovieType.fromValue(checkNotNull(savedStateHandle["movieType"]))
    private val movieId: Long = checkNotNull(savedStateHandle["movieId"])

    private val replaySharedFlow = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val movieDetailUiStateFlow: StateFlow<MovieDetailUiState> = replaySharedFlow
        .flatMapLatest {
            movieDetailUiState(movieId, getMovieDetailUseCase, getCreditsUseCase, getVideosUseCase, appPreferencesSettingsRepository)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MovieDetailUiState.Loading,
        )

    val isBookmarked: StateFlow<Boolean> = appPreferencesSettingsRepository.userSettings
        .map { it.bookmarkedMovieIds.contains(movieId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    private val _postBookmarkUiState: MutableStateFlow<PostBookmarkUiState> = MutableStateFlow(PostBookmarkUiState.Success)
    val postBookmarkUiState: StateFlow<PostBookmarkUiState> = _postBookmarkUiState.asStateFlow()

    val movieDetailUiEvent: MutableSharedFlow<MovieDetailUiEvent> = MutableSharedFlow()

    fun updateBookmark(movieDetail: MovieDetail, isBookmarked: Boolean) = bookmarkUseCase
        .invoke(movieType, UserMovie(movieDetail, isBookmarked))
        .asResult()
        .onStart {
            _postBookmarkUiState.emit(PostBookmarkUiState.Loading)
        }
        .onEach {
            when (it) {
                is ResultState.Success -> {
                    _postBookmarkUiState.emit(PostBookmarkUiState.Success)
                    movieDetailUiEvent.emit(MovieDetailUiEvent.ShowBookmarkedMessage(isBookmarked, movieDetail.id))
                }

                is ResultState.Failure<*> -> {
                    _postBookmarkUiState.emit(PostBookmarkUiState.Error(it.e))
                }
            }
        }
        .launchIn(viewModelScope)

    fun replay() {
        replaySharedFlow.tryEmit(Unit)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private fun movieDetailUiState(
    movieId: Long,
    getMovieDetailUseCase: GetMovieDetailUseCase,
    getMovieCreditsUseCase: GetCreditsUseCase,
    getVideosUseCase: GetVideosUseCase,
    appPreferencesSettingsRepository: AppPreferencesRepository,
): Flow<MovieDetailUiState> {
    val appLanguage = appPreferencesSettingsRepository
        .userSettings
        .map { it.appLanguage }

    return appLanguage
        .flatMapLatest { language ->
            combine(
                getMovieDetailUseCase.invoke(movieId, language.value),
                getMovieCreditsUseCase.invoke(movieId, language.value),
                getVideosUseCase.invoke(movieId),
                ::Triple,
            )
        }
        .asResult()
        .map { movieDetailResult ->
            when (movieDetailResult) {
                is ResultState.Success -> {
                    MovieDetailUiState.Success(
                        MovieDetailResult(
                            movieDetail = movieDetailResult.data.first,
                            movieCredits = movieDetailResult.data.second,
                            movieVideos = movieDetailResult.data.third,
                        ),
                    )
                }

                is ResultState.Failure<*> -> {
                    MovieDetailUiState.Error(movieDetailResult.e)
                }
            }
        }
        .onStart { emit(MovieDetailUiState.Loading) }
}
