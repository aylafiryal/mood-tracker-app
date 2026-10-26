package com.example.moodtrackerapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.moodtrackerapp.R
import com.example.moodtrackerapp.model.Mood

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    // Simpan mood yang dipilih (null jika belum ada yang dipilih)
    var selectedMood by remember { mutableStateOf<Mood?>(null) }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.6f),
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.tertiary
        ) {
            // 1 COLUMN UTAMA: Mengatur jarak antar 4 Row di dalamnya tanpa Spacer
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                //verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                verticalArrangement = Arrangement.SpaceBetween
                // Catatan: Jika ingin baris tombol nempel di paling bawah ala Bootstrap Card Footer,
                // ganti baris di atas dengan: verticalArrangement = Arrangement.SpaceBetween
            ) {
                // ROW 1: Icon (Full width / 12 col)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Example Icon",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }

                // ROW 2: Title (Full width / 12 col)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
//                    Text(
//                        text = dialogTitle,
//                        style = MaterialTheme.typography.headlineSmall,
//                        color = MaterialTheme.colorScheme.onTertiary
//                    )
                    Text(
                        text = selectedMood?.label ?: "Pilih perasaanmu",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }

                // ROW 3: Grid Pilihan Mood (Dynamic Looping per 3 Kolom)
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
//                                mood.iconRes?.let { resId ->
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

                // ROW 4: Action Buttons (Full width / 12 col)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Dismiss", color = MaterialTheme.colorScheme.onTertiary)
                    }
                    TextButton(onClick = onConfirmation) {
                        Text("Confirm", color = MaterialTheme.colorScheme.onTertiary)
                    }
                }
            }
        }
    }
}