package com.example.moodtrackerapp.moodcalendar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moodtrackerapp.moodcalendar.components.calendar.CalendarCell
import com.example.moodtrackerapp.moodcalendar.components.calendar.WeekdayCell
import com.example.moodtrackerapp.util.formatToWeekDay
import com.example.moodtrackerapp.util.getWeekDays
import kotlinx.collections.immutable.ImmutableList
import java.util.*

@Composable
fun CalendarGrid(
    date: ImmutableList<Pair<Date, Boolean>>,
    onClick: (Date) -> Unit,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier,
) {
    val weekdayFirstDay = date.first().first.formatToWeekDay()
    val weekdays = getWeekDays(startFromSunday)
    com.example.moodtrackerapp.moodcalendar.CalendarCustomLayout(modifier = modifier) {
        weekdays.forEach {
            WeekdayCell(weekday = it)
        }
        // Adds Spacers to align the first day of the month to the correct weekday
        val firstDayOfWeek = if (startFromSunday) 1 else 2
        repeat((weekdayFirstDay - firstDayOfWeek + 7) % 7) {
            Spacer(modifier = Modifier)
        }
        date.forEach {
            CalendarCell(
                date = it.first,
                signal = it.second,
                onClick = { onClick(it.first) })
        }
    }
}