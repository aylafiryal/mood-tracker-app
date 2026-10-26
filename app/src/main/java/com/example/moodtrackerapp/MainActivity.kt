package com.example.moodtrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.moodtrackerapp.moodcalendar.CalendarView
import com.example.moodtrackerapp.util.addMonths
import com.example.moodtrackerapp.util.getDatesOfMonth
// Import package baru yang benar
import com.example.moodtrackerapp.ui.components.AlertDialogExample
import com.example.moodtrackerapp.ui.theme.MoodTrackerAppTheme
import com.example.moodtrackerapp.ui.theme.darkText
import com.example.moodtrackerapp.util.formatToCalendarDay
import kotlinx.collections.immutable.toImmutableList
import java.util.Calendar
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContent {
//            MoodTrackerAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }

        /*
        NOTES:
        `setContent` adalah fungsi dari `ComponentActivity`. Dia nerima 2 param:
            1. `parent` tapi udah ada default value
            2. `content` yang merupakan fungsi

         Pemanggilan ini kalo dijadiin panjang:
            setContent(parent) { content }` <- pake konsep `trailing lambda`.
         */
        setContent {
            MoodTrackerAppTheme {
                /*
                NOTES:
                Fungsi `Scaffold` butuh banyak parameter. Tapi mostly, semuanya punya default value.
                Yang belum cuma `content` dan dia butuh parameter bertipe `PaddingValues`.
                Waktu manggil fungsi ini, `trailing lambda` langsung tau kalo ini itu mengisi param `content` karena cuma
                param lambda itu yang butuh parameter. Dan nama parameter `PaddingValues` ditulis secara eksplisit dengan nama `innerPadding`
                 */

                /*
                NOTES (rumus fungsi lambda): { parameter -> kode_yang_dieksekusi }
                 */
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.darkText
                ) { innerPadding ->
                    /*
                        NOTES:
                        "Month" adalah nama objek MutableStateOf yang valuenya berupa objek Date.
                        Fungsi dari MutableStateOf adalah agar nilainya bisa diamati oleh Compose.
                     */
                    var month by remember { mutableStateOf(Date()) }
                    /*
                        NOTES: Masih bingung yang ini ???
                     */
                    val dates = remember(month) {
                        month.getDatesOfMonth().map { date ->
                            val cal = Calendar.getInstance().apply { time = date }
                            val day = cal.get(Calendar.DAY_OF_MONTH)
                            date to (day % 2 == 0) // Tanggal genap = true (ada signal)
                        }.toImmutableList()
                    }

                    // ✅ Ubah jadi rememberSaveable
                    var selectedDate by rememberSaveable { mutableStateOf<Date?>(null) }

                    /*
                        NOTES:
                        Fungsi "remember" berguna untuk mempertahankan nilai sebuah variable.
                        Kalau mau pakai keyword "by", hasil di dalam blok remember mesti yang bisa didelegasikan.
                        Tipe data hasil yang bisa didelegasikan syaratnya adalah: punya fungsi getValue.
                        Biar kalo mau ubah value, bisa langsung dari variabelnya sendiri.
                     */

                    // 1. Pakai rememberSaveable agar state dialog tidak ter-reset saat recomposition/rotasi
                    var showMoodDialog by rememberSaveable { mutableStateOf(false) }

                    CalendarView(
                        month = month,
                        date = dates,
                        displayNext = true,
                        displayPrev = true,
                        onClickNext = { month = month.addMonths(1) },
                        onClickPrev = { month = month.addMonths(-1) },
                        onClick = { clickedDate ->
                            selectedDate = clickedDate
                            showMoodDialog = true // Trigger dialog baru di sini
                        },
                        startFromSunday = true,
                        modifier = Modifier.padding(innerPadding)
                    )

                    // Panggil AlertDialogExample di luar onClick, secara kondisional
//                    selectedDate?.let { date ->
//                        AlertDialogExample(
//                            onDismissRequest = { selectedDate = null },
//                            onConfirmation = { selectedDate = null },
//                            dialogTitle = "Catat Mood",
//                            dialogText = "Kamu memilih tanggal ${date.formatToCalendarDay()}",
//                            icon = Icons.Default.Info
//                        )
//                    }
                    if (showMoodDialog && selectedDate != null) {
                        AlertDialogExample(
                            onDismissRequest = {
                                showMoodDialog = false // Cukup tutup dialog tanpa langsung mengganggu state selectedDate
                            },
                            onConfirmation = {
                                // Simpan mood ke database di sini
                                showMoodDialog = false
                            },
                            dialogTitle = "Catat Mood",
                            dialogText = "Kamu memilih tanggal ${selectedDate?.formatToCalendarDay()}",
                            icon = Icons.Default.Info
                        )
                    }
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MoodTrackerAppTheme {
//        Greeting("Android")
//    }
//}