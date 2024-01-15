package com.seosh817.moviehub.core.data.repository

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.seosh817.moviehub.core.database.dao.MovieDao
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import com.seosh817.moviehub.core.domain.repository.UserMovieRepository
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CompositeMovieRepository @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDao: MovieDao,
    private val appPreferencesRepository: AppPreferencesRepository,
) : UserMovieRepository {

    override fun observeUserMovies(language: String?): Flow<PagingData<UserMovie>> =
        movieRepository.fetchPopularMovies(language)
            .combine(appPreferencesRepository.userSettings) { pagingData, userSettings ->
                pagingData.map { movieOverview ->
                    UserMovie(movieOverview, userSettings)
                }
            }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    override fun observeAllBookmarked(language: String?): Flow<PagingData<UserMovie>> {
//        return appPreferencesRepository.userSettings.map { it.bookmarkedMovieIds }
//            .distinctUntilChanged()
//            .flatMapLatest { bookmarked ->
//                when {
//                    bookmarked.isEmpty() -> flowOf(PagingData.empty())
//                    else -> observeUserMovies(language).map { pagingData ->
//                        pagingData.filter { userMovie ->
//                            userMovie.isBookmarked
//                        }
//                    }
//                }
//            }
//    }
}