package com.example.moodtrackerapp.moodcalendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CalendarCustomLayout(
    modifier: Modifier = Modifier,
    horizontalGapDp: Dp = 2.dp,
    verticalGapDp: Dp = 2.dp,
    weekdayHeightDp: Dp = 40.dp, // 1. Tentukan tinggi khusus baris hari di sini
    content: @Composable () -> Unit,
) {
    val horizontalGap = with(LocalDensity.current) { horizontalGapDp.roundToPx() }
    val verticalGap = with(LocalDensity.current) { verticalGapDp.roundToPx() }
    val weekdayHeight = with(LocalDensity.current) { weekdayHeightDp.roundToPx() }

    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val totalWidthWithoutGap = constraints.maxWidth - (horizontalGap * 6)
        val singleWidth = totalWidthWithoutGap / 7

        // 2. Hitung jumlah baris khusus tanggal (dikurangi 7 item pertama milik hari)
        val dateItemsCount = if (measurables.size > 7) measurables.size - 7 else 0
        val dateRows = if (dateItemsCount > 0) ((dateItemsCount - 1) / 7) + 1 else 1

        // 3. Sisa tinggi layar dibagikan khusus untuk baris tanggal saja
        val totalVerticalGap = verticalGap * dateRows
        val remainingHeightForDates = constraints.maxHeight - weekdayHeight - totalVerticalGap
        val dateHeight = (remainingHeightForDates / dateRows).coerceAtLeast(0)

        val xPos = mutableListOf<Int>()
        val yPos = mutableListOf<Int>()
        var currentX = 0
        var currentY = 0

        measurables.forEachIndexed { index, _ ->
            xPos.add(currentX)
            yPos.add(currentY)

            // Tentukan tinggi item saat ini (7 item pertama pakai weekdayHeight)
            val currentItemHeight = if (index < 7) weekdayHeight else dateHeight

            if ((index + 1) % 7 == 0) {
                currentX = 0
                currentY += currentItemHeight + verticalGap
            } else {
                currentX += singleWidth + horizontalGap
            }
        }

        // 4. Ukur tiap item sesuai kategorinya (Hari vs Tanggal)
        val placeables = measurables.mapIndexed { index, measurable ->
            val targetHeight = if (index < 7) weekdayHeight else dateHeight
            measurable.measure(
                constraints.copy(
                    minWidth = singleWidth,
                    maxWidth = singleWidth,
                    minHeight = targetHeight,
                    maxHeight = targetHeight
                )
            )
        }

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = xPos[index],
                    y = yPos[index],
                )
            }
        }
    }
}