package com.seosh817.moviehub.feature.movies

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.seosh817.moviehub.core.domain.usecase.GetPopularMoviesUseCase
import com.seosh817.moviehub.core.model.MovieOverview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _pagingMoviesStateFlow: Flow<PagingData<MovieOverview>> = MutableStateFlow<PagingData<MovieOverview>>(
        PagingData.empty()
    )

    private val _refreshSharedFlow = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    val moviesPagingFlow = getPopularMoviesUseCase.invoke()

//    val uiState = _uiState.asStateFlow()

//    val moviesPagingFlow =
//        _refreshSharedFlow.flatMapLatest {
//            getPopularMoviesUseCase
//                .invoke()
//        }
//        .onStart {
//            _isRefreshing.emit(true)
//        }
//        .distinctUntilChanged()
//        .catch {
//            Log.d("!!!", "mediator paging viewmodel error: $it")
//            _isRefreshing.emit(false)
//        }
//        .onEach {
//            _isRefreshing.emit(false)
//        }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000L),
//            initialValue = PagingData.empty()
//        )

//    private val _uiState = MutableStateFlow<TrendingUiState>(
//        TrendingUiState.Success(PagingData.empty())
//    )
//    val uiState = _uiState.asStateFlow()

//    val pagingMoviesStateFlow: Flow<PagingData<MovieOverview>> =
//        getPopularMoviesUseCase
//            .invoke()
//            .onStart {
//                _isRefreshing.emit(true)
//            }
//            .distinctUntilChanged()
//            .catch {
//                Log.d("!!!", "mediator paging viewmodel error: $it")
//                _isRefreshing.emit(false)
//            }
//            .onEach {
//                _isRefreshing.emit(false)
//            }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000L),
//                initialValue = PagingData.empty()
//            )

//    private val _refreshSharedFlow = MutableSharedFlow<Unit>(replay = 1).apply {
//        tryEmit(Unit)
//    }
//
//    val pagingMoviesStateFlow: Flow<PagingData<MovieOverview>> =
//        _refreshSharedFlow
//            .flatMapLatest {
//                getPopularMoviesUseCase
//                    .invoke()
//            }
//            .onStart {
//                _isRefreshing.emit(true)
//            }
//            .distinctUntilChanged()
//            .catch {
//                Log.d("!!!", "mediator paging viewmodel error: $it")
//                _isRefreshing.emit(false)
//            }
//            .onEach {
//                _isRefreshing.emit(false)
//            }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000L),
//                initialValue = PagingData.empty()
//            )

    /*init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase.invoke().collectLatest { pagingData ->
//                _uiState.value = TrendingUiState.Success(pagingData)
            }
        }
    }*/

    fun getMovies(): Flow<PagingData<MovieOverview>> {
        return getPopularMoviesUseCase.invoke()
    }

    var showRefreshError by mutableStateOf(false)

    fun onSwipeRefresh() = viewModelScope.launch {
    }

    fun showMessage() {
        showRefreshError = true
    }

    fun hideMessage() {
        showRefreshError = false
    }
}