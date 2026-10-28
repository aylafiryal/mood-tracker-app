package com.example.moodtrackerapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoodDao {
    // semua mood untuk semua tanggal
    @Query("SELECT * FROM mood_entry")
    suspend fun getAll(): List<MoodEntity>

    // mood per tanggal (format ISO "yyyy-MM-dd")
    @Query("SELECT * FROM mood_entry WHERE date = :date")
    suspend fun loadAllByDate(date: String): List<MoodEntity>

    // insert mood untuk sebuah tanggal
    @Insert
    suspend fun insert(entry: MoodEntity)

    // delete mood dari sebuah tanggal
    @Delete
    suspend fun delete(entry: MoodEntity)
}
