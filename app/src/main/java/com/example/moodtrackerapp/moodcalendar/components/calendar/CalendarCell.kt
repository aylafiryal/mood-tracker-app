package com.example.moodtrackerapp.moodcalendar.components.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodtrackerapp.data.local.MoodEntity
import com.example.moodtrackerapp.util.formatToCalendarDay
import java.time.LocalDate

@Composable
fun CalendarCell(
    date: LocalDate,
    listMoodEntity: List<MoodEntity>?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = date.formatToCalendarDay()
    Box(
        modifier = modifier
            .padding(1.dp)
            .background(
//                shape = RoundedCornerShape(CornerSize(8.dp)),
                color = colorScheme.secondary
            )
//            .clip(RoundedCornerShape(CornerSize(8.dp)))
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            color = colorScheme.onSecondaryContainer,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 6.dp, top = 4.dp)
        )

        listMoodEntity?.let {
            Text(
                text = it.get(0).mood.label, // atau properti emoji/icon dari entity kamu
                fontSize = 12.sp,
                color = colorScheme.onSecondary
            )

            listMoodEntity.forEach { rowMoods ->
                Row(
                    modifier = Modifier
                    //modifier = Modifier.fillMaxWidth(),
                    //horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val mood = rowMoods.mood
                    val resId = mood.iconRes

                    if (resId != null && resId != 0) {
                        Image(
                            painter = painterResource(id = resId),
                            contentDescription = mood.label,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}