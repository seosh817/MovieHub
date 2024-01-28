package com.seosh817.moviehub.feature.bookmarks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.seosh817.moviehub.core.domain.usecase.GetFavoritesUseCase
import com.seosh817.moviehub.core.domain.usecase.GetPopularMoviesUseCase
import com.seosh817.moviehub.core.model.UserMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class BookmarksViewModel @Inject constructor(
    getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

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
                initialValue = PagingData.empty()
            )

    var showRefreshError by mutableStateOf(false)

    fun updateBookmarked(bookmark: Boolean) {
//        viewModelScope.launch {
//            appPreferencesSettingsRepository.setBookMarkedMovieIds(movieId, isChecked)
//        }
    }

    fun showMessage() {
        showRefreshError = true
    }

    fun hideMessage() {
        showRefreshError = false
    }
}