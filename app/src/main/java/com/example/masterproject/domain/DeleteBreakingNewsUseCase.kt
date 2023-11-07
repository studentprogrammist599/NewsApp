package com.example.masterproject.domain

import com.example.masterproject.datasource.IDatasource
import com.example.masterproject.domain.model.BreakingNewsDomainModel

class DeleteBreakingNewsUseCase(
    private val repository: IDatasource
) {
    suspend fun execute(breakingNewsDomainModel: BreakingNewsDomainModel) =
        repository.deleteFavoriteBreakingNews(breakingNewsDomainModel)
}