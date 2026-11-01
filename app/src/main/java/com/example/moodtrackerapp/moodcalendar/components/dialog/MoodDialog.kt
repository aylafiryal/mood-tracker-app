package com.example.moodtrackerapp.moodcalendar.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.window.DialogProperties
import com.example.moodtrackerapp.model.Mood

@Composable
fun MoodDialog(
    onDismiss: () -> Unit,
    onMoodSelected: (Mood?) -> Unit
) {
    /*
        NOTES:
        Fungsi "remember" berguna untuk mempertahankan nilai sebuah variable.
        Kalau mau pakai keyword "by", hasil di dalam blok remember mesti yang bisa didelegasikan.
        Tipe data hasil yang bisa didelegasikan syaratnya adalah: punya fungsi getValue.
        Biar kalo mau ubah value, bisa langsung dari variabelnya sendiri.
    */
    // Simpan mood yang dipilih (null jika belum ada yang dipilih)
    var selectedMood by remember { mutableStateOf<Mood?>(null) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.tertiary
        ) {
            // 1 COLUMN UTAMA: Mengatur jarak antar 4 Row di dalamnya tanpa Spacer
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                // ROW 1: Title (Full width / 12 col)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = selectedMood?.label ?: "Pilih perasaanmu",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // ROW 2: Grid Pilihan Mood (Dynamic Looping per 3 Kolom)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Memecah enum Mood menjadi baris-baris berisi maksimal 3 item
                    val moodRows = Mood.entries.chunked(3)

                    moodRows.forEach { rowMoods ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Loop tiap item mood di dalam baris
                            rowMoods.forEach { mood ->
                                val isSelected = selectedMood == mood
                                // ✅ Pengecekan aman: pastikan iconRes tidak null DAN bernilai valid (> 0)
                                val resId = mood.iconRes

                                // Jika iconRes ada nilainya, tampilkan Gambar
                                if (resId != null && resId != 0) {
                                    Image(
                                        painter = painterResource(id = resId),
                                        contentDescription = mood.label,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
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
                                            .clickable { selectedMood = mood }
                                            .padding(4.dp)
                                    )
                                } else {
                                    // Dummy placeholder jika iconRes belum diisi di Enum
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            // DUMMY COL: Menjaga ukuran col seragam jika jumlah item di baris < 3
                            repeat(3 - rowMoods.size) {
                                Spacer(modifier = Modifier.weight(1f))
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
                            onMoodSelected(selectedMood)
                            onDismiss()
                        }
                    ) {
                        Text("Confirm", color = MaterialTheme.colorScheme.onTertiary, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}