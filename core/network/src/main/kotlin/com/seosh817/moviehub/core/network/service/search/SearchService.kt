package com.seosh817.moviehub.core.network.service.search

import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale

interface SearchService {

    @GET("/3/search/movie")
    suspend fun searchMovies(@Query("query") query: String, @Query("page") page: Int, @Query("language") language: String? = Locale.getDefault().toString()): Response<NetworkMoviesResponse>
}
