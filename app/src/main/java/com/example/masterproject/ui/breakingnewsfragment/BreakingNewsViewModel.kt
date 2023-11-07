package com.example.masterproject.ui.breakingnewsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masterproject.domain.DeleteBreakingNewsUseCase
import com.example.masterproject.domain.GetAllFavoriteBreakingNews
import com.example.masterproject.domain.GetBreakingNewsUseCase
import com.example.masterproject.domain.SaveBreakingNewsUseCase
import com.example.masterproject.domain.model.BreakingNewsDomainModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BreakingNewsViewModel(
    getAllFavoriteBreakingNews: GetAllFavoriteBreakingNews,
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    private val saveBreakingNewsUseCase: SaveBreakingNewsUseCase,
    private val deleteBreakingNewsUseCase: DeleteBreakingNewsUseCase,
) : ViewModel() {

    private val favoriteNews: StateFlow<List<BreakingNewsDomainModel>> = getAllFavoriteBreakingNews
        .execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    private val _news = MutableStateFlow<BreakingNewsState>(BreakingNewsState.Success())
    val news: StateFlow<BreakingNewsState>
        get() = _news.asStateFlow()

    fun getNews() {
        _news.value = BreakingNewsState.Loading
        viewModelScope.launch(
            Dispatchers.IO
                    + CoroutineExceptionHandler { _, throwable ->
                errorCatch(throwable)
            }
        ) {
            _news.value = BreakingNewsState.Success(getBreakingNewsUseCase.execute())
        }
    }

    fun isAFavoriteNews(breakingNewsDomainModel: BreakingNewsDomainModel): Boolean {
        return favoriteNews.value.contains(breakingNewsDomainModel)
    }

    fun favoriteUserClickHandler(breakingNewsDomainModel: BreakingNewsDomainModel): Boolean {
        return when (isAFavoriteNews(breakingNewsDomainModel)) {
            true -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteBreakingNewsUseCase.execute(breakingNewsDomainModel)
                }
                false
            }

            false -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveBreakingNewsUseCase.execute(breakingNewsDomainModel)
                }
                true
            }
        }
    }

    private fun errorCatch(throwable: Throwable) {
        _news.value = BreakingNewsState.Error(error = throwable.message.toString())
    }
}