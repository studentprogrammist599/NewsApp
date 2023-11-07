package com.example.masterproject.datasource.database

import com.example.masterproject.datasource.database.model.FavoriteNewsEntity
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(
    private val database: AppDatabase
) : IDatabaseRepository {
    override suspend fun saveFavoriteBreakingNews(breakingNewsEntity: FavoriteNewsEntity) {
        database.favoriteNewsDao.insert(breakingNewsEntity)
    }

    override suspend fun deleteFavoriteBreakingNews(breakingNewsEntity: FavoriteNewsEntity) {
        database.favoriteNewsDao.delete(breakingNewsEntity)
    }

    override fun getAllFavoriteBreakingNews(): Flow<List<FavoriteNewsEntity>> {
        return database.favoriteNewsDao.getAll()
    }
}