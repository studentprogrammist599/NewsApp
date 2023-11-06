package com.example.masterproject.ui.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.masterproject.domain.GetTestDataUseCase

class MainViewModel(
    private val getTestDataUseCase: GetTestDataUseCase
) : ViewModel() {

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result

    fun getData() {
        _result.value = getTestDataUseCase.execute()
    }
}