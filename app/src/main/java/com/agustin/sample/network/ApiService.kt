package com.agustin.sample.network

import com.agustin.sample.network.models.ApiResponse
import com.agustin.sample.network.models.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
    ): ApiResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
    ): ApiResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
    ): ApiResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): MovieDetail

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String
    ): ApiResponse
}