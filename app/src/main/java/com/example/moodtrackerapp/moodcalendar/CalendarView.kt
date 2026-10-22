package com.example.moodtrackerapp.moodcalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moodtrackerapp.util.formatToMonthString
import com.example.moodtrackerapp.ui.components.CustomIconButton
import kotlinx.collections.immutable.ImmutableList
import java.util.*

@Composable
fun CalendarView(
    month: Date,
    date: ImmutableList<Pair<Date, Boolean>>?,
    displayNext: Boolean,
    displayPrev: Boolean,
    onClickNext: () -> Unit,
    onClickPrev: () -> Unit,
    onClick: (Date) -> Unit,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.tertiary)
        ) {
            if (displayPrev)
                CustomIconButton(
                    onClick = onClickPrev,
                    modifier = Modifier.align(Alignment.CenterStart),
                    icon = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "navigate to previous month"
                )
            if (displayNext)
                CustomIconButton(
                    onClick = onClickNext,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    icon = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "navigate to next month"
                )
            Text(
                text = month.formatToMonthString(),
                style = typography.headlineMedium,
                color = colorScheme.onPrimaryContainer,
                modifier = Modifier.align(Alignment.Center),
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        if (!date.isNullOrEmpty()) {
            CalendarGrid(
                date = date,
                onClick = onClick,
                startFromSunday = startFromSunday,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .background( color = MaterialTheme.colorScheme.background )
            )
        }
    }
}