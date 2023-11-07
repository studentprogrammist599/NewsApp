package com.example.masterproject.domain

import com.example.masterproject.datasource.IDatasource

class GetAllFavoriteBreakingNews(
    private val repository: IDatasource
) {
    fun execute() = repository.getAllFavoriteBreakingNews()
}