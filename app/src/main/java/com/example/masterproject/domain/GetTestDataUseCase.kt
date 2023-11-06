package com.example.masterproject.domain

import com.example.masterproject.datasource.IDatasource

class GetTestDataUseCase(
    private val dataSource: IDatasource
) {
    fun execute(): String {
        return dataSource.getTestData()
    }
}