package com.example.moodtrackerapp.moodcalendar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.moodtrackerapp.model.Mood
import com.example.moodtrackerapp.util.formatToCalendarDay
import com.example.moodtrackerapp.util.formatToMonthString
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Composable
fun MoodDetailDialog(
    selectedDate: LocalDate,
    onDismiss: () -> Unit,
    moodList: ImmutableList<Mood>,
) {
    val formattedDate = "${selectedDate.formatToCalendarDay()} ${selectedDate.formatToMonthString()}"

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.tertiary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Judul Tanggal
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                // Row ditaruh DI DALAM Column
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    // Ukuran tiap mood tetap 1/3 lebar (maks 3 mood), jadi ukuran dialog
                    // sama berapa pun jumlah moodnya: 1 di tengah, 2 dibagi rata, 3 penuh.
                    val itemSize = maxWidth / 3
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        moodList.forEach { mood ->
                            val resId = mood.iconRes

                            if (resId != null && resId != 0) {
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = mood.label,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .size(itemSize)
                                        .clip(RoundedCornerShape(10.dp))
                                )
                            } else {
                                Spacer(modifier = Modifier.size(itemSize))
                            }
                        }
                    }
                }
            }
        }
    }
}