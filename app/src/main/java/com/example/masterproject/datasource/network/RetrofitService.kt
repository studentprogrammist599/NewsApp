package com.example.masterproject.datasource.network

import com.example.masterproject.datasource.network.model.BreakingNewsDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("/v2/top-headlines?country=us")
    suspend fun getBreakingNews(
        @Query("apikey") apiKey: String = "e8f656fe729842b792d2cbb5d2180b93",
    ): BreakingNewsDTO
}