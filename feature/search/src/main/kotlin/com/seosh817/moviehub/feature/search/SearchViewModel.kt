package com.seosh817.moviehub.feature.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.asResult
import com.seosh817.moviehub.core.domain.usecase.GetSearchMoviesUseCase
import com.seosh817.moviehub.core.domain.usecase.PostBookmarkUseCase
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.model.UserMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    getSearchMoviesUseCase: GetSearchMoviesUseCase,
    private val postBookmarkUseCase: PostBookmarkUseCase
) : ViewModel() {

    private val _queryStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    val queryStateFlow: StateFlow<String> = _queryStateFlow.asStateFlow()

    private val _searchUiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState.Success)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    val searchUiEvent: MutableSharedFlow<SearchUiEvent> = MutableSharedFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchImagePagingItems: Flow<PagingData<UserMovie>> = _queryStateFlow
        .debounce(DEBOUNCE_TIME)
        .filter {
            it.trim().isNotEmpty()
        }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getSearchMoviesUseCase(
                query = query,
                scope = viewModelScope
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = PagingData.empty()
        )

    init {
        handleSearchUiEvent()
    }

    private fun handleSearchUiEvent() {
        viewModelScope.launch {
            searchUiEvent
                .collectLatest { searchUiEvent ->
                    when (searchUiEvent) {
                        is SearchUiEvent.OnQueryChanged -> onQueryChanged(searchUiEvent.query)
                        is SearchUiEvent.OnBookmarkClick -> onClickBookmark(searchUiEvent.userMovie)
                        is SearchUiEvent.ClearSearchQuery -> onQueryChanged("")
                        else -> {}
                    }
                }
        }
    }

    private fun onQueryChanged(query: String) {
        _queryStateFlow.value = query
    }

    fun sendEvent(event: SearchUiEvent) {
        viewModelScope.launch {
            searchUiEvent.emit(event)
        }
    }

    private fun onClickBookmark(userMovie: UserMovie) = postBookmarkUseCase
        .invoke(MovieType.POPULAR, userMovie)
        .asResult()
        .onStart {
            _searchUiState.emit(SearchUiState.Loading)
        }
        .onEach {
            when (it) {
                is ResultState.Success -> {
                    searchUiEvent.emit(SearchUiEvent.ShowBookmarkedMessage(userMovie.isBookmarked, userMovie.id))
                    _searchUiState.emit(SearchUiState.Success)
                }

                is ResultState.Failure<*> -> {
                    Log.d("!!!", "error: ${it.e}")
                    _searchUiState.emit(SearchUiState.Error(it.e))
                }
            }
        }
        .launchIn(viewModelScope)

    companion object {
        private const val DEBOUNCE_TIME = 1_000L
    }
}