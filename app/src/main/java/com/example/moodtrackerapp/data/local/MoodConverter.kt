package com.example.moodtrackerapp.data.local

import androidx.room.TypeConverter
import com.example.moodtrackerapp.model.Mood

class MoodConverter {
    @TypeConverter
    fun fromMood(mood: Mood): String = mood.apiKey

    @TypeConverter
    fun toMood(value: String): Mood = Mood.fromApiKey(value)
}