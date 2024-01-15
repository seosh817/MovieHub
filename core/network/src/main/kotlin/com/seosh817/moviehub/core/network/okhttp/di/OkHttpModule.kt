package com.seosh817.moviehub.core.network.okhttp.di

import android.content.Context
import com.seosh817.moviehub.core.network.BuildConfig
import com.seosh817.moviehub.core.network.okhttp.interceptor.CookiesInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpModule {

    private const val HTTP_CACHE_SIZE_BYTES = 1024 * 1024 * 50L

    private const val CALL_TIMEOUT_SECONDS = 0L

    private const val CONNECT_TIMEOUT_SECONDS = 3L

    private const val READ_TIMEOUT_SECONDS = 3L

    private const val WRITE_TIMEOUT_SECONDS = 3L

    @Provides
    @Singleton
    fun httpCache(
        @ApplicationContext context: Context
    ): Cache {
        return Cache(context.cacheDir, HTTP_CACHE_SIZE_BYTES)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else level
        }
    }

    @Provides
    @Singleton
    fun provideCookiesInterceptor(): CookiesInterceptor {
        return CookiesInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cookiesInterceptor: CookiesInterceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(cookiesInterceptor)
            .callTimeout(CALL_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .followRedirects(true)
            .followSslRedirects(true)
            .cache(cache)
            .build()
    }
}