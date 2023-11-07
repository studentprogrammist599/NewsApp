package com.example.masterproject.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.masterproject.datasource.database.dao.FavoriteNewsDao
import com.example.masterproject.datasource.database.model.FavoriteNewsEntity

@Database(entities = [FavoriteNewsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val favoriteNewsDao: FavoriteNewsDao

}