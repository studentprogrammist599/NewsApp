package com.example.masterproject.datasource

import com.example.masterproject.domain.model.BreakingNewsDomainModel
import kotlinx.coroutines.flow.Flow

interface IDatasource {

    suspend fun getBreakingNewsFromNetwork(): List<BreakingNewsDomainModel>
    suspend fun saveFavoriteBreakingNews(breakingNewsDomainModel: BreakingNewsDomainModel)
    suspend fun deleteFavoriteBreakingNews(breakingNewsDomainModel: BreakingNewsDomainModel)
    fun getAllFavoriteBreakingNews(): Flow<List<BreakingNewsDomainModel>>
}