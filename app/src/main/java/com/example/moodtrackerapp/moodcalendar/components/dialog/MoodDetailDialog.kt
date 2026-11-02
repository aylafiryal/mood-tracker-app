package com.example.moodtrackerapp.moodcalendar.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.moodtrackerapp.R
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
    onAddNewMood: () -> Unit,
    onDeleteMood: () -> Unit,
) {
    val formattedDate = "${selectedDate.formatToCalendarDay()} ${selectedDate.formatToMonthString()}"
    //val isAddDisabled = moodList.size >= 3
    val isAddDisabled = remember(moodList) { moodList.size >= 3 }

    var selectedMood by remember { mutableStateOf<Mood?>(null) }

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
                            .height(110.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        moodList.forEach { mood ->
                            val isSelected = selectedMood == mood
                            val resId = mood.iconRes

                            if (resId != null && resId != 0) {
                                // Box Luar: Memberikan ruang (padding) agar offset ikon tidak terpotong dialog
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .aspectRatio(1f)
                                        .padding(8.dp) // PENTING: Ruang aman untuk ikon keluar border
                                ) {
                                    // 1. Box Komponen Utama (Gambar + Border)
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(10.dp))
                                            .then(
                                                if (isSelected) {
                                                    Modifier.border(
                                                        width = 3.dp,
                                                        color = MaterialTheme.colorScheme.onTertiary,
                                                        shape = RoundedCornerShape(10.dp)
                                                    )
                                                } else Modifier
                                            )
                                            .clickable {
                                                selectedMood = if (selectedMood == mood) null else mood
                                            }
                                    ) {
                                        Image(
                                            painter = painterResource(id = resId),
                                            contentDescription = mood.label,
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }

                                    // 2. Ikon Overlay (Delete) - Diletakkan di luar Box inner agar posisinya berada DI ATAS border
                                    if (isSelected) {
                                        Image(
                                            painter = painterResource(id = R.drawable.delete_circle),
                                            contentDescription = "Unselect",
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .align(Alignment.TopEnd) // Pojok kanan atas (atau Alignment.TopStart untuk kiri atas)
                                                .offset(x = 6.dp, y = (-6).dp) // Menggeser ikon tepat menimpa garis border
                                        )
                                    }
                                }
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