package com.seosh817.moviehub.core.network.service.movie

import com.seosh817.moviehub.core.network.model.movie_detail.MovieDetailEntity
import com.seosh817.moviehub.core.network.model.movie_list.MovieResponseEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale

interface MovieService {

    @GET("/3/movie/popular")
    suspend fun fetchPopularMovies(@Query("page") page: Int, @Query("language") language: String? = Locale.getDefault().toString()): Response<MovieResponseEntity>

    @GET("/3/movie/top_rated")
    suspend fun fetchTopRatedMovies(@Query("page") page: Int, @Query("language") language: String? = Locale.getDefault().toString()): Response<MovieResponseEntity>

    @GET("/3/movie/{movie_id}/reviews")
    suspend fun fetchUpcomingMovies(@Query("page") page: Int, @Query("language") language: String? = Locale.getDefault().toString()): Response<MovieResponseEntity>

    @GET("/3/movie/{movie_id}")
    suspend fun fetchMovieDetail(@Query("movie_id") movieId: Int, @Query("language") language: String? = Locale.getDefault().toString()): Response<MovieDetailEntity>
}
