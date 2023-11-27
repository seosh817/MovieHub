package com.seosh817.moviehub.core.domain.usecase.movies

import androidx.paging.PagingData
import com.seosh817.moviehub.core.domain.repository.MovieRepository
import com.seosh817.moviehub.core.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMoviesUseCase {

    override operator fun invoke(language: String): Flow<PagingData<Movie>> = movieRepository.fetchPopularMovies(language)
}
