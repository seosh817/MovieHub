package com.seosh817.moviehub.core.network.service.movie

import com.seosh817.moviehub.core.network.model.credits.NetworkCredits
import com.seosh817.moviehub.core.network.model.movie_detail.NetworkMovieDetail
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Locale

interface MovieService {

    @GET("/3/movie/popular")
    suspend fun fetchPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String? = Locale.getDefault().toString()
    ): Response<NetworkMoviesResponse>

    @GET("/3/movie/popular")
    suspend fun fetchPopularMovies2(
        @Query("page") page: Int,
        @Query("language") language: String? = Locale.getDefault().toString()
    ): NetworkMoviesResponse

    @GET("/3/movie/top_rated")
    suspend fun fetchTopRatedMovies(
        @Query("page") page: Int,
        @Query("language") language: String? = Locale.getDefault().toString()
    ): Response<NetworkMoviesResponse>

    @GET("/3/movie/{movie_id}/reviews")
    suspend fun fetchUpcomingMovies(
        @Query("page") page: Int,
        @Query("language") language: String? = Locale.getDefault().toString()
    ): Response<NetworkMoviesResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun fetchMovieDetail(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String? = Locale.getDefault().toString()
    ): Response<NetworkMovieDetail>

    @GET("/3/movie/{movie_id}/credits")
    suspend fun fetchCredits(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String? = Locale.getDefault().toString()
    ): Response<NetworkCredits>
}
