package com.personalprojects.clockcheck.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.personalprojects.clockcheck.database.dao.ResultDAO
import com.personalprojects.clockcheck.database.entities.ResultEntity

@Database(entities = [ResultEntity::class], version = 1)
abstract class ResultDatabase : RoomDatabase() {
    abstract fun resultDao(): ResultDAO
}
