package com.example.newsapp2.network

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.newsapp2.model.TianApiResponse
import retrofit2.http.Path

interface NewsApiService {
    @GET("{category}/index")
    suspend fun getHeadlines(
        @Path("category") category: String,
        @Query("key") apiKey: String,
    ): TianApiResponse
}