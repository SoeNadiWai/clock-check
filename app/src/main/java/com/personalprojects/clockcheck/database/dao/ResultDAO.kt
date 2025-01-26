package com.personalprojects.clockcheck.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.personalprojects.clockcheck.database.entities.ResultEntity

@Dao
interface ResultDAO {
    @Query("SELECT * FROM resultDatabase")
    fun getAll(): List<ResultEntity>

    @Insert
    fun insertAll(vararg results: ResultEntity)
}