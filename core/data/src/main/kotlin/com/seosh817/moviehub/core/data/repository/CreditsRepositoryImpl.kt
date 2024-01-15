package com.seosh817.moviehub.core.data.repository

import com.seosh817.common.result.ResultState
import com.seosh817.common.result.extension.map
import com.seosh817.moviehub.core.domain.repository.CreditsRepository
import com.seosh817.moviehub.core.model.Credits
import com.seosh817.moviehub.core.data.model.asExternalModel
import com.seosh817.moviehub.core.network.model.credits.NetworkCredits
import com.seosh817.moviehub.core.network.source.CreditsRemoteDataSource
import javax.inject.Inject

class CreditsRepositoryImpl @Inject constructor(
    private val creditsRemoteDataSource: CreditsRemoteDataSource
) : CreditsRepository {

    override suspend fun fetchCredits(movieId: Long, language: String?): ResultState<Credits> {
        return creditsRemoteDataSource.fetchCredits(movieId, language)
            .map(NetworkCredits::asExternalModel)
    }
}
