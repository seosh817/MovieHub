package com.seosh817.moviehub.network.service.movie_list

import com.seosh817.moviehub.network.model.movie_list.MovieResponseEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/3/movie/popular")
    suspend fun fetchPopularMovies(@Query("page") page: Int, @Query("language") language: String = "ko-KR"): Response<MovieResponseEntity>

    @GET("/3/movie/top_rated")
    suspend fun fetchTopRatedMovies(@Query("page") page: Int, @Query("language") language: String = "ko-KR"): Response<MovieResponseEntity>

    @GET("/3/movie/{movie_id}/reviews")
    suspend fun fetchUpcomingMovies(@Query("page") page: Int, @Query("language") language: String = "ko-KR"): Response<MovieResponseEntity>
}