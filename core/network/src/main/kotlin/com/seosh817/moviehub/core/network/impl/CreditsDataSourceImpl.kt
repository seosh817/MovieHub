package com.seosh817.moviehub.core.network.impl

import com.seosh817.common.network.handleApi
import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.model.Credits
import com.seosh817.moviehub.core.network.mapper.asExternalModel
import com.seosh817.moviehub.core.network.service.movie.MovieService
import com.seosh817.moviehub.core.network.source.CreditsRemoteDataSource
import javax.inject.Inject

class CreditsDataSourceImpl @Inject constructor(
    private val movieService: MovieService
) : CreditsRemoteDataSource {
    override suspend fun fetchCredits(movieId: Long, language: String?): ResultState<Credits> {
        return handleApi {
            movieService.fetchCredits(movieId, language)
        }
            .map {
                it.asExternalModel()
            }
    }
}
