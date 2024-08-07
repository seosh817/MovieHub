package com.seosh817.moviehub.core.network.source

import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.model.Credits
import com.seosh817.moviehub.core.network.model.credits.NetworkCredits

interface CreditsRemoteDataSource {

    suspend fun fetchCredits(movieId: Long, language: String?): ResultState<NetworkCredits>
}
