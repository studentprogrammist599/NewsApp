package com.example.masterproject.datasource.network.model

data class BreakingNewsDTO(
    val articles: List<ArticleDTO>,
    val status: String?,
    val totalResults: Int?
)