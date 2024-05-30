package com.example.workoutapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_database")
data class HistoryEntity(
    @PrimaryKey
    val date:String//upto milliseconds
)

