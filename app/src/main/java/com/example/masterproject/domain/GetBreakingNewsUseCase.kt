package com.example.masterproject.domain

import com.example.masterproject.datasource.IDatasource

class GetBreakingNewsUseCase(
    private val repository: IDatasource
) {
    suspend fun execute() = repository.getBreakingNewsFromNetwork()
}