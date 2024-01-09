package com.seosh817.moviehub.feature.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.asResult
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.usecase.movie_detail.GetMovieDetailUseCase
import com.seosh817.moviehub.core.domain.usecase.movies.GetCreditsUseCase
import com.seosh817.moviehub.core.model.MovieDetailResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val appPreferencesSettingsRepository: AppPreferencesRepository,
    savedStateHandle: SavedStateHandle,
    getMovieDetailUseCase: GetMovieDetailUseCase,
    getMovieCreditsUseCase: GetCreditsUseCase,
) : ViewModel() {

    private val movieId: Long = checkNotNull(savedStateHandle["movieId"])

    val movieDetailUiStateFlow: StateFlow<MovieDetailUiState> = movieDetailUiState(movieId, getMovieDetailUseCase, getMovieCreditsUseCase)
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

    fun updateBookmarkedMovieId(isChecked: Boolean) {
        viewModelScope.launch {
            appPreferencesSettingsRepository.setBookMarkedMovieIds(movieId, isChecked)
        }
    }
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
