package com.seosh817.moviehub.feature.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.asResult
import com.seosh817.moviehub.core.domain.usecase.GetPopularMoviesUseCase
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val postBookmarkUseCase: PostBookmarkUseCase
) : ViewModel() {

    val pagingMoviesStateFlow: Flow<PagingData<UserMovie>> =
        getPopularMoviesUseCase
            .invoke(scope = viewModelScope)
            .onStart {
                _isRefreshing.emit(true)
            }
            .distinctUntilChanged()
            .onEach {
                _isRefreshing.emit(false)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = PagingData.empty()
            )

    private val _postBookmarkUiState: MutableStateFlow<PostBookmarkUiState> = MutableStateFlow(PostBookmarkUiState.Success)
    val postBookmarkUiState: StateFlow<PostBookmarkUiState> = _postBookmarkUiState.asStateFlow()

    private val _moviesUiEvent = MutableSharedFlow<MoviesUiEvent>()
    val moviesUiEvent: Flow<MoviesUiEvent> = _moviesUiEvent.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun onBookmarkClick(movieId: Long, isBookmarked: Boolean) =
        postBookmarkUseCase.invoke(MovieType.POPULAR, movieId, isBookmarked)
            .asResult()
            .onStart {
                _postBookmarkUiState.emit(PostBookmarkUiState.Loading)
            }
            .onEach {
                when (it) {
                    is ResultState.Success -> {
                        _moviesUiEvent.emit(MoviesUiEvent.ShowBookmarkedMessage(isBookmarked))
                        _postBookmarkUiState.emit(PostBookmarkUiState.Success)
                    }

                    is ResultState.Failure<*> -> {
                        _postBookmarkUiState.emit(PostBookmarkUiState.Error(it.e))
                    }
                }
            }
            .launchIn(viewModelScope)


    fun showMessage() {
        viewModelScope.launch {
            _moviesUiEvent.emit(MoviesUiEvent.ShowRefreshErrorMessage)
        }
    }

    fun hideMessage() {
        viewModelScope.launch {
            _moviesUiEvent.emit(MoviesUiEvent.HideRefreshErrorMessage)
        }
    }
}
