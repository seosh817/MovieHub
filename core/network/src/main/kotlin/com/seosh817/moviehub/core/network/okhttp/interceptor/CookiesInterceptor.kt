package com.seosh817.moviehub.core.network.okhttp.interceptor

import com.seosh817.moviehub.core.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CookiesInterceptor(

) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val newHttpUrl = originalRequest.url
            .newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.TMDB_API_KEY)
            .build()


        val newRequest = originalRequest
            .newBuilder()
            .url(newHttpUrl)
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val API_KEY = "api_key"
    }
}