package com.personalprojects.clockcheck.home

import androidx.room.PrimaryKey

data class ResultData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val searchTime: Int,
    val startTime: Int,
    val endTime: Int
)