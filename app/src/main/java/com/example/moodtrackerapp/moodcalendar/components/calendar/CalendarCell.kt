package com.example.moodtrackerapp.moodcalendar.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodtrackerapp.util.formatToCalendarDay
import java.util.Date

@Composable
fun CalendarCell(
    date: Date,
    signal: Boolean,
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

        if (signal) {
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .size(10.dp)
                    .background(
                        shape = CircleShape,
                        color = colorScheme.tertiary
                    )
                    .align(Alignment.TopStart)
            )
        }
    }
}