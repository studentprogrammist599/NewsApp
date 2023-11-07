package com.example.masterproject

import android.app.Application
import androidx.room.Room
import com.example.masterproject.datasource.Datasource
import com.example.masterproject.datasource.IDatasource
import com.example.masterproject.datasource.database.AppDatabase
import com.example.masterproject.datasource.database.DatabaseRepository
import com.example.masterproject.datasource.database.IDatabaseRepository
import com.example.masterproject.datasource.network.INetworkRepository
import com.example.masterproject.datasource.network.NetworkRepository
import com.example.masterproject.datasource.network.RetrofitService
import com.example.masterproject.utils.NetworkObserver
import com.example.masterproject.utils.imageloader.CoilImageLoader
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class App : Application() {

    private val iNetworkRepository: INetworkRepository
        get() = NetworkRepository(retrofitService = retrofitService)

    private val iDatabaseRepository: IDatabaseRepository
        get() = DatabaseRepository(database)

    private val iDataSource: IDatasource
        get() = Datasource(
            iDatabaseRepository = iDatabaseRepository,
            iNetworkRepository = iNetworkRepository,
        )

    val networkObserver by lazy { NetworkObserver(instance) }
    val coilImageLoader = CoilImageLoader()

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private var _instance: App? = null
        val instance
            get() = _instance!!

        private const val DB_NAME = "database.db"
        private val database by lazy {
            Room.databaseBuilder(instance, AppDatabase::class.java, DB_NAME)
                .build()
        }

        private const val BASE_URL = "https://newsapi.org/"
        private val retrofitService: RetrofitService =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
    }
}