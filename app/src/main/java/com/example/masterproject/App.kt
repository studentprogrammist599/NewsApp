package com.example.masterproject

import android.app.Application
import com.example.masterproject.datasource.Datasource
import com.example.masterproject.datasource.IDatasource
import com.example.masterproject.domain.GetTestDataUseCase

class App: Application() {

    private val iDataSource: IDatasource
        get() = Datasource()

    val getTestDataUseCase: GetTestDataUseCase
        get() = GetTestDataUseCase(iDataSource)

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private var _instance: App? = null
        val instance
            get() = _instance!!
    }
}