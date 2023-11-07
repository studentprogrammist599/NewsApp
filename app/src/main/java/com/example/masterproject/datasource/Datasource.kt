package com.example.masterproject.datasource

import com.example.masterproject.datasource.database.IDatabaseRepository
import com.example.masterproject.datasource.network.INetworkRepository
import com.example.masterproject.domain.model.BreakingNewsDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Datasource(
    private val iNetworkRepository: INetworkRepository,
    private val iDatabaseRepository: IDatabaseRepository,
) : IDatasource {

    override suspend fun getBreakingNewsFromNetwork(): List<BreakingNewsDomainModel> {
        return iNetworkRepository.getBreakingNews().toListBreakingNewsDomainModel()
    }

    override suspend fun saveFavoriteBreakingNews(breakingNewsDomainModel: BreakingNewsDomainModel) {
        iDatabaseRepository.saveFavoriteBreakingNews(breakingNewsDomainModel.toFavoriteNewsEntity())
    }

    override suspend fun deleteFavoriteBreakingNews(breakingNewsDomainModel: BreakingNewsDomainModel) {
        iDatabaseRepository.deleteFavoriteBreakingNews(breakingNewsDomainModel.toFavoriteNewsEntity())
    }

    override fun getAllFavoriteBreakingNews(): Flow<List<BreakingNewsDomainModel>> {
        return iDatabaseRepository.getAllFavoriteBreakingNews().map {
            it.toListBreakingNewsDomainModel()
        }
    }
}