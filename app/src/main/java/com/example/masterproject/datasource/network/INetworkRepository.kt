package com.example.masterproject.datasource.network

import com.example.masterproject.datasource.network.model.BreakingNewsDTO

interface INetworkRepository {

    suspend fun getBreakingNews(): BreakingNewsDTO
}