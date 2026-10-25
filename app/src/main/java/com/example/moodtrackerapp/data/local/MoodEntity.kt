package com.example.moodtrackerapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moodtrackerapp.model.Mood

@Entity(tableName = "mood_entry")
data class MoodEntity(
    @PrimaryKey val date: String, // Misal ISO Format: "2026-10-25"
    val mood: Mood,               // Otomatis di-convert jadi TEXT via MoodConverter
    val note: String? = null
)