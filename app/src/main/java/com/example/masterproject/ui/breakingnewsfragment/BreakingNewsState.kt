package com.example.masterproject.ui.breakingnewsfragment

import com.example.masterproject.domain.model.BreakingNewsDomainModel

sealed class BreakingNewsState {

    data class Success(val response: List<BreakingNewsDomainModel> = emptyList()) :
        BreakingNewsState()

    data class Error(val error: String) : BreakingNewsState()
    object Loading : BreakingNewsState()
}
