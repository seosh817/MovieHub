package com.seosh817.moviehub.core.common.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val moviehubDispatchers: MovieHubDispatchers)

enum class MovieHubDispatchers {
    DEFAULT,
    IO
}
