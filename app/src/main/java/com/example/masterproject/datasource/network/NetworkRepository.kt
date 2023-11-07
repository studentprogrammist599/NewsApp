package com.example.masterproject.datasource.network

import com.example.masterproject.datasource.network.model.BreakingNewsDTO

class NetworkRepository(
    private val retrofitService: RetrofitService
) : INetworkRepository {

    override suspend fun getBreakingNews(): BreakingNewsDTO {
        return retrofitService.getBreakingNews()
    }
}