package com.example.moodtrackerapp.moodcalendar.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    moodList: ImmutableList<Mood>,
    onDismiss: () -> Unit,
    onAddNewMood: () -> Unit
) {
    val formattedDate = "${selectedDate.formatToCalendarDay()} ${selectedDate.formatToMonthString()}"
    //val isAddDisabled = moodList.size >= 3
    val isAddDisabled = remember(moodList) { moodList.size >= 3 }

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

                // Pakai Column biasa (tanpa BoxWithConstraints)
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 1. Berikan tinggi fixed pada area ikon (misal 100.dp - sesuaikan kebutuhan visualmu)
                    // 2. Arrangement.SpaceEvenly otomatis menangani posisi 1, 2, atau 3 item!
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp), // Menjaga tinggi dialog tetap fixed & tidak berubah-ubah
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
                                        .weight(1f, fill = false) // Masing-masing gambar maksimal mengambil 1/3 slot
                                        .fillMaxHeight()           // Mengisi tinggi Row yang sudah fixed
                                        .clip(shape = RoundedCornerShape(10.dp))
                                )
                            }
                        }
                    }
                }

                // ROW 3: Action Buttons (Full width / 12 col)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Dismiss", color = MaterialTheme.colorScheme.onTertiary, fontSize = 18.sp)
                    }
                    TextButton(
                        onClick = {
                            onAddNewMood()
                        },
                        enabled = !isAddDisabled,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            disabledContentColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.38f)
                        )
                    ) {
                        Text("Add", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}