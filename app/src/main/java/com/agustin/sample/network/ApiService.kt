package com.agustin.sample.network

import com.agustin.sample.network.models.ApiMessage
import retrofit2.http.GET

interface ApiService {

    @GET("9b58f48a-048a-4676-a888-ffbeb5e7b762")
    suspend fun getMessages(): List<ApiMessage>

}