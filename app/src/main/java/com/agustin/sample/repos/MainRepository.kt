package com.agustin.sample.repos

import com.agustin.sample.coroutines.DispatcherProvider
import com.agustin.sample.network.ApiService
import com.agustin.sample.network.models.Movie
import kotlinx.coroutines.withContext

class MainRepository(
    private val api: ApiService,
    private val dispatcher: DispatcherProvider
) {

    suspend fun getTopRatedMovies(): List<Movie> =
        withContext(dispatcher.io()) {
            api.getTopRatedMovies().movies
        }
}