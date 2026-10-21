package com.example.moodtrackerapp.util

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.formatToCalendarDay(): String = SimpleDateFormat("d", Locale.getDefault()).format(this)

fun Int.getDayOfWeek3Letters(): String? = Calendar.getInstance().apply {
    set(Calendar.DAY_OF_WEEK, this@getDayOfWeek3Letters)
}.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())

fun Date.formatToWeekDay(): Int =
    Calendar.getInstance().apply { time = this@formatToWeekDay }.get(Calendar.DAY_OF_WEEK)

fun getWeekDays(startFromSunday: Boolean): ImmutableList<Int> {
    val lista = (1..7).toList()
    return (if (startFromSunday) lista else lista.drop(1) + lista.take(1)).toImmutableList()
}

fun Date.formatToMonthString(): String = SimpleDateFormat("MMMM", Locale.getDefault()).format(this)

fun Date.getDatesOfMonth(): ImmutableList<Date> {
    val cal = Calendar.getInstance().apply { time = this@getDatesOfMonth }
    val totalDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    return (1..totalDays).map { day ->
        cal.set(Calendar.DAY_OF_MONTH, day)
        cal.time
    }.toImmutableList()
}

fun Date.addMonths(amount: Int): Date =
    Calendar.getInstance().apply {
        time = this@addMonths
        add(Calendar.MONTH, amount)
    }.time