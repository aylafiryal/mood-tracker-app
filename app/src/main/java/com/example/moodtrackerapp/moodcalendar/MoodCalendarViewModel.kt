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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class MoodCalendarViewModel(private val repository: MoodDao): ViewModel() {
    /*
        NOTES:
        "Month" adalah nama objek MutableStateOf yang valuenya berupa objek LocalDate.
        Fungsi dari MutableStateOf adalah agar nilainya bisa diamati oleh Compose.
    */
    var month by mutableStateOf(LocalDate.now())
        private set

    val dates get() = month.getDatesOfMonth()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    var showMoodDialog by mutableStateOf(false)
        private set

    var showMoodDetailDialog by mutableStateOf(false)
        private set

    // Mengubah Flow<List<MoodEntity>> menjadi StateFlow<Map<LocalDate, MoodEntity>>
    val moodMap: StateFlow<Map<LocalDate, List<MoodEntity>>> = repository.getAll()
        .map { list ->
            list.groupBy { entity ->
                // Parsing String "yyyy-MM-dd" dari database ke LocalDate
                LocalDate.parse(entity.date)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyMap()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val moodByDate: StateFlow<ImmutableList<Mood>> = _selectedDate
        .flatMapLatest { date ->
            repository.loadAllByDate(date.toString())
        }
        .map { entities ->
            entities.map { it.mood }
                .toImmutableList()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = persistentListOf()
        )

    fun nextMonth() { month = month.addMonths(1) }
    fun previousMonth() { month = month.addMonths(-1) }

    fun onDateSelected(date: LocalDate, showDetail: Boolean) {
        _selectedDate.value = date
        if(showDetail) {
            showMoodDetailDialog = true
        } else {
            showMoodDialog = true
        }
    }

    fun dismissDialog() {
        showMoodDialog = false
        showMoodDetailDialog = false
    }

    fun saveMood(mood: Mood, date: LocalDate) {
        Log.d("LOG_MOOD", "Tanggal $date mood-nya adalah $mood")
        // Panggil database/repository di sini
        viewModelScope.launch {
            repository.insert(MoodEntity(date = date.toString(), mood = mood))
        }
        showMoodDialog = false
    }
}