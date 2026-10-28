package com.example.moodtrackerapp.moodcalendar

// Import package baru yang benar
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.moodtrackerapp.moodcalendar.components.MoodDialog
import com.example.moodtrackerapp.ui.theme.darkText
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodtrackerapp.data.local.MoodCalendarViewModelFactory
import com.example.moodtrackerapp.data.local.MoodDatabase

@Composable
fun MoodCalendarScreen(
    context: Context = LocalContext.current
) {
    // 1. Ambil instance database & repository/dao
    val database = MoodDatabase.getDatabase(context)
    val dao = database.moodDao()

    // 2. Buat instance Factory
    val factory = MoodCalendarViewModelFactory(dao) // Sesuaikan parameternya

    // 3. Masukkan factory ke fungsi viewModel()
    val viewModel: MoodCalendarViewModel = viewModel(factory = factory)

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
        CalendarView(
            month = viewModel.month,
            date = viewModel.dates,
            displayNext = true,
            displayPrev = true,
            onClickNext = { viewModel.nextMonth() },
            onClickPrev = { viewModel.previousMonth() },
            onClick = { clickedDate ->
                viewModel.onDateSelected(clickedDate)
            },
            startFromSunday = true,
            modifier = Modifier.padding(innerPadding)
        )

        if (viewModel.showMoodDialog && viewModel.selectedDate != null) {
            MoodDialog(onDismiss = {
                viewModel.dismissDialog()
            }, onMoodSelected = { mood ->
                mood?.let { viewModel.saveMood(it, viewModel.selectedDate) }
            })
        }
    }
}