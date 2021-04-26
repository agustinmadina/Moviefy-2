package com.agustin.sample.network

import com.agustin.sample.network.models.ApiResponse
import com.agustin.sample.network.models.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String? = null
    ): ApiResponse

}