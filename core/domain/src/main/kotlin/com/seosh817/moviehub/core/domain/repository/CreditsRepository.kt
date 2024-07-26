package com.seosh817.moviehub.core.domain.repository

import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.model.Credits

interface CreditsRepository {

    suspend fun fetchCredits(movieId: Long, language: String?): ResultState<Credits>
}
