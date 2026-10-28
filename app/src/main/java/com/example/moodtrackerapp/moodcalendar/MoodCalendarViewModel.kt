package com.example.moodtrackerapp.moodcalendar

import android.app.Application
import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodtrackerapp.data.local.MoodDao
import com.example.moodtrackerapp.data.local.MoodDatabase
import com.example.moodtrackerapp.data.local.MoodEntity
import com.example.moodtrackerapp.model.Mood
import com.example.moodtrackerapp.util.addMonths
import com.example.moodtrackerapp.util.getDatesOfMonth
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MoodCalendarViewModel(private val repository: MoodDao): ViewModel() {
    /*
        NOTES:
        "Month" adalah nama objek MutableStateOf yang valuenya berupa objek Date.
        Fungsi dari MutableStateOf adalah agar nilainya bisa diamati oleh Compose.
    */
    var month by mutableStateOf(Date())
        private set

    val dates by derivedStateOf {
        month.getDatesOfMonth().map { date ->
            val cal = Calendar.getInstance().apply { time = date }
            val day = cal.get(Calendar.DAY_OF_MONTH)
            date to (day % 2 == 0)
        }.toImmutableList()
    }

    var selectedDate by mutableStateOf(Date())
        private set

    var showMoodDialog by mutableStateOf(false)
        private set

    fun nextMonth() { month = month.addMonths(1) }
    fun previousMonth() { month = month.addMonths(-1) }

    fun onDateSelected(date: Date) {
        selectedDate = date
        showMoodDialog = true
    }

    fun dismissDialog() {
        showMoodDialog = false
    }

    fun saveMood(mood: Mood, date: Date) {
        Log.d("LOG_MOOD", "Tanggal $date mood-nya adalah $mood")
        // Panggil database/repository di sini
        viewModelScope.launch {
            val isoDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date)
            repository.insert(MoodEntity(date = isoDate, mood = mood))
        }
        showMoodDialog = false
    }
}