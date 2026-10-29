package com.example.moodtrackerapp.util

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

fun LocalDate.formatToCalendarDay(): String = dayOfMonth.toString()

fun Int.getDayOfWeek3Letters(): String? = Calendar.getInstance().apply {
    set(Calendar.DAY_OF_WEEK, this@getDayOfWeek3Letters)
}.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())

// Sunday = 1 ... Saturday = 7 (sama seperti Calendar.DAY_OF_WEEK)
fun LocalDate.formatToWeekDay(): Int = dayOfWeek.value % 7 + 1

fun getWeekDays(startFromSunday: Boolean): ImmutableList<Int> {
    val lista = (1..7).toList()
    return (if (startFromSunday) lista else lista.drop(1) + lista.take(1)).toImmutableList()
}

fun LocalDate.formatToMonthString(): String =
    format(DateTimeFormatter.ofPattern("MMMM", Locale.getDefault()))

fun LocalDate.getDatesOfMonth(): ImmutableList<LocalDate> =
    (1..lengthOfMonth()).map { withDayOfMonth(it) }.toImmutableList()

fun LocalDate.addMonths(amount: Int): LocalDate = plusMonths(amount.toLong())
