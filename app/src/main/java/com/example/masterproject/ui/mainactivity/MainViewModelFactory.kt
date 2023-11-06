package com.example.masterproject.ui.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.masterproject.domain.GetTestDataUseCase

class MainViewModelFactory(private val getTestDataUseCase: GetTestDataUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getTestDataUseCase = getTestDataUseCase) as T
    }
}