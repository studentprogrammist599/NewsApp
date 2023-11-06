package com.example.masterproject

import android.app.Application
import com.example.masterproject.datasource.Datasource
import com.example.masterproject.datasource.IDatasource

class App: Application() {

    private val iDataSource: IDatasource = Datasource()

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