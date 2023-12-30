package com.seosh817.moviehub.feature.movie_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.domain.usecase.movie_detail.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private val movieId: Long = checkNotNull(savedStateHandle["movieId"])

    val movieDetailUiStateFlow = getMovieDetailUseCase
        .invoke(movieId)
        .map {
            when (it) {
                is ResultState.Success -> MovieDetailUiState.Success(it.data)
                is ResultState.Failure<*> -> MovieDetailUiState.Error(it.e)
            }
        }
        .onStart { emit(MovieDetailUiState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MovieDetailUiState.Loading,
        )
}