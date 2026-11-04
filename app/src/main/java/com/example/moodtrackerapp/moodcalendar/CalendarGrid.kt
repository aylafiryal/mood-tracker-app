package com.example.moodtrackerapp.moodcalendar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moodtrackerapp.data.local.MoodEntity
import com.example.moodtrackerapp.moodcalendar.components.calendar.CalendarCell
import com.example.moodtrackerapp.moodcalendar.components.calendar.WeekdayCell
import com.example.moodtrackerapp.util.formatToWeekDay
import com.example.moodtrackerapp.util.getWeekDays
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Composable
fun CalendarGrid(
    date: ImmutableList<LocalDate>,
    moodMap: Map<LocalDate, List<MoodEntity>>,
    onClick: (LocalDate) -> Unit,
    onDeleteClick: (MoodEntity) -> Unit,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier,
) {
    val weekdayFirstDay = date.first().formatToWeekDay()
    val weekdays = getWeekDays(startFromSunday)
    CalendarCustomLayout(modifier = modifier) {
        weekdays.forEach {
            WeekdayCell(weekday = it)
        }
        // Adds Spacers to align the first day of the month to the correct weekday
        val firstDayOfWeek = if (startFromSunday) 1 else 2
        repeat((weekdayFirstDay - firstDayOfWeek + 7) % 7) {
            Spacer(modifier = Modifier)
        }
        date.forEach { itemDate ->
            CalendarCell(
                date = itemDate,
                listMoodEntity = moodMap[itemDate],
                onClick = { onClick(itemDate) },
                onDeleteClick = { moodMap[itemDate]?.forEach { moodEntity -> onDeleteClick(moodEntity) } }
            )
        }
    }
}