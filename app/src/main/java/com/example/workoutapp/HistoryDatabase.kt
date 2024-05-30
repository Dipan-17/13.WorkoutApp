package com.example.workoutapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class],version=1)
abstract class HistoryDatabase:RoomDatabase() {
    abstract fun historyDao():HistoryDAO

    companion object{
        @Volatile //changes made by one thread visible to all
        private var INSTANCE:HistoryDatabase?=null

        fun getInstance(context: Context):HistoryDatabase{//if there is an instance then return it else create one
            kotlin.synchronized(this){
                var instance= INSTANCE

                if(instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "workout_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE=instance
                }
                return instance
            }
        }

    }

}