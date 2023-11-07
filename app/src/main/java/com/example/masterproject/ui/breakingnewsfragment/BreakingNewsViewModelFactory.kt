package com.example.masterproject.ui.breakingnewsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.masterproject.domain.DeleteBreakingNewsUseCase
import com.example.masterproject.domain.GetAllFavoriteBreakingNews
import com.example.masterproject.domain.GetBreakingNewsUseCase
import com.example.masterproject.domain.SaveBreakingNewsUseCase

class BreakingNewsViewModelFactory(
    private val getAllFavoriteBreakingNews: GetAllFavoriteBreakingNews,
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    private val saveBreakingNewsUseCase: SaveBreakingNewsUseCase,
    private val deleteBreakingNewsUseCase: DeleteBreakingNewsUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BreakingNewsViewModel(
            getAllFavoriteBreakingNews = getAllFavoriteBreakingNews,
            getBreakingNewsUseCase = getBreakingNewsUseCase,
            saveBreakingNewsUseCase = saveBreakingNewsUseCase,
            deleteBreakingNewsUseCase = deleteBreakingNewsUseCase,
        ) as T
    }
}