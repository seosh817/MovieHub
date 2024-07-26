package com.seosh817.moviehub.core.domain.repository

import com.seosh817.moviehub.core.model.AppVersions

interface AppVersionsRepository {

    fun getAppVersion(): AppVersions
}
