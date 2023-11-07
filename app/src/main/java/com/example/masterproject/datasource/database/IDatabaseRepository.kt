package com.example.masterproject.datasource.database

import com.example.masterproject.datasource.database.model.FavoriteNewsEntity
import kotlinx.coroutines.flow.Flow

interface IDatabaseRepository {

    suspend fun saveFavoriteBreakingNews(breakingNewsEntity: FavoriteNewsEntity)
    suspend fun deleteFavoriteBreakingNews(breakingNewsEntity: FavoriteNewsEntity)
    fun getAllFavoriteBreakingNews(): Flow<List<FavoriteNewsEntity>>
}