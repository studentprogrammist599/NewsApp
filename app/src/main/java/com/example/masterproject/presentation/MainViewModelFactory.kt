package com.example.masterproject.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.masterproject.datasource.Datasource
import com.example.masterproject.domain.GetTestDataUseCase

class MainViewModelFactory() : ViewModelProvider.Factory {

    private val datasource by lazy {
        Datasource()
    }

    private val getTestDataUseCase by lazy {
        GetTestDataUseCase(dataSource = datasource)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getTestDataUseCase = getTestDataUseCase) as T
    }
}