package com.example.moodtrackerapp.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import java.util.concurrent.Executors

@Database(entities = [MoodEntity::class], version = 1)
@TypeConverters(MoodConverter::class)
abstract class MoodDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao

    companion object {
        @Volatile
        private var INSTANCE: MoodDatabase? = null

        fun getDatabase(context: Context): MoodDatabase {
            // If the instance is already created, return it
            return INSTANCE ?: synchronized(this) {
                // Double-check to prevent race conditions
                val instance = INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, // Secures the Application Context safely
                    MoodDatabase::class.java,
                    "mood-tracker-app-database"
                )
                    .setQueryCallback({ sqlQuery, bindArgs ->
                        android.util.Log.d("ROOM_LOG", "Query: $sqlQuery | Args: $bindArgs")
                    }, Executors.newSingleThreadExecutor())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}