package com.example.moodtrackerapp.data.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moodtrackerapp.moodcalendar.MoodCalendarViewModel

class MoodCalendarViewModelFactory(
    private val repository: MoodDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoodCalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MoodCalendarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}