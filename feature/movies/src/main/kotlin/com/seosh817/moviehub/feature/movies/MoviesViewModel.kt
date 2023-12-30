package com.seosh817.moviehub.feature.movies

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.seosh817.moviehub.core.domain.usecase.movies.GetPopularMoviesUseCase
import com.seosh817.moviehub.core.model.MovieOverview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MoviesViewModel @Inject constructor(
    getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _refreshSharedFlow = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    val pagingMoviesStateFlow: Flow<PagingData<MovieOverview>> =
        _refreshSharedFlow
            .flatMapLatest {
                getPopularMoviesUseCase
                    .invoke()
            }
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
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = PagingData.empty()
            )

    var showRefreshError by mutableStateOf(false)

    fun onSwipeRefresh() = viewModelScope.launch {
        _refreshSharedFlow.emit(Unit)
    }

    fun showMessage() {
        showRefreshError = true
    }

    fun hideMessage() {
        showRefreshError = false
    }
}