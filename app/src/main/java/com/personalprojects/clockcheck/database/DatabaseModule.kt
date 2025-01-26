package com.personalprojects.clockcheck.database

import android.app.Application
import androidx.room.Room
import com.personalprojects.clockcheck.database.dao.ResultDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): ResultDatabase {
        return Room.databaseBuilder(app, ResultDatabase::class.java, "result.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideResultDao(database: ResultDatabase): ResultDAO {
        return database.resultDao()
    }
}
