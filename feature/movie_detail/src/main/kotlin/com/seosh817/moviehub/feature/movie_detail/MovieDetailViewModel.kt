package com.seosh817.moviehub.feature.movie_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.asResult
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.usecase.GetCreditsUseCase
import com.seosh817.moviehub.core.domain.usecase.GetMovieDetailUseCase
import com.seosh817.moviehub.core.domain.usecase.PostBookmarkUseCase
import com.seosh817.moviehub.core.model.MovieDetailResult
import com.seosh817.moviehub.core.model.MovieType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val bookmarkUseCase: PostBookmarkUseCase,
    savedStateHandle: SavedStateHandle,
    appPreferencesSettingsRepository: AppPreferencesRepository,
    getMovieDetailUseCase: GetMovieDetailUseCase,
    getCreditsUseCase: GetCreditsUseCase,
) : ViewModel() {

    private val movieType: MovieType = MovieType.fromValue(checkNotNull(savedStateHandle["movieType"]))
    private val movieId: Long = checkNotNull(savedStateHandle["movieId"])

    private val _postDetailUiState: MutableStateFlow<PostDetailUiState> = MutableStateFlow(PostDetailUiState.Success)
    val postDetailUiState: StateFlow<PostDetailUiState> = _postDetailUiState.asStateFlow()

    private var _showBookmarkSnackbar = mutableStateOf(false)
    val showBookmarkSnackbar: State<Boolean> = _showBookmarkSnackbar

    val movieDetailUiStateFlow: StateFlow<MovieDetailUiState> = movieDetailUiState(movieId, getMovieDetailUseCase, getCreditsUseCase)
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

    fun updateBookmark(isBookmarked: Boolean) = bookmarkUseCase
        .invoke(movieType, movieId, isBookmarked)
        .asResult()
        .onStart {
            _postDetailUiState.emit(PostDetailUiState.Loading)
            _showBookmarkSnackbar.value = false
        }
        .onEach {
            _showBookmarkSnackbar.value = true
            when (it) {
                is ResultState.Success -> {
                    _postDetailUiState.emit(PostDetailUiState.Success)
                }

                is ResultState.Failure<*> -> {
                    _postDetailUiState.emit(PostDetailUiState.Error(it.e))
                }
            }
        }
        .launchIn(viewModelScope)
}

private fun movieDetailUiState(
    movieId: Long,
    getMovieDetailUseCase: GetMovieDetailUseCase,
    getMovieCreditsUseCase: GetCreditsUseCase
): Flow<MovieDetailUiState> {

    val movieDetails = getMovieDetailUseCase.invoke(movieId)
    val movieCredits = getMovieCreditsUseCase.invoke(movieId)

    return combine(
        movieDetails,
        movieCredits,
        ::Pair,
    ).asResult()
        .map { movieDetailResult ->
            when (movieDetailResult) {
                is ResultState.Success -> {
                    MovieDetailUiState.Success(
                        MovieDetailResult(
                            movieDetail = movieDetailResult.data.first,
                            movieCredits = movieDetailResult.data.second
                        )
                    )
                }

                is ResultState.Failure<*> -> {
                    MovieDetailUiState.Error(movieDetailResult.e)
                }
            }
        }
        .onStart { emit(MovieDetailUiState.Loading) }
}
