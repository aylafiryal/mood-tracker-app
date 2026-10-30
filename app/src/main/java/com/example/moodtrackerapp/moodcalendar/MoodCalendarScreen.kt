package com.example.moodtrackerapp.moodcalendar

// Import package baru yang benar
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodtrackerapp.moodcalendar.components.MoodDialog
import com.example.moodtrackerapp.ui.theme.darkText
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodtrackerapp.data.local.MoodCalendarViewModelFactory
import com.example.moodtrackerapp.data.local.MoodDatabase
import com.example.moodtrackerapp.moodcalendar.components.MoodDetailDialog
import java.time.LocalDate

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

    val moodMap by viewModel.moodMap.collectAsState()

    val moodByDateState by viewModel.moodByDate.collectAsStateWithLifecycle()

    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()

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
            moodMap = moodMap,
            displayNext = true,
            displayPrev = true,
            onClickNext = { viewModel.nextMonth() },
            onClickPrev = { viewModel.previousMonth() },
            onClick = { clickedDate ->
                if(moodMap[clickedDate] == null) {
                    viewModel.onDateSelected(clickedDate, false)
                } else {
                    viewModel.onDateSelected(clickedDate, true)
                }
            },
            startFromSunday = true,
            modifier = Modifier.padding(innerPadding)
        )

        if (viewModel.showMoodDialog && selectedDate != null) {
            MoodDialog(onDismiss = {
                viewModel.dismissDialog()
            }, onMoodSelected = { mood ->
                mood?.let { viewModel.saveMood(it, selectedDate) }
            })
        }

        if (viewModel.showMoodDetailDialog && selectedDate != null) {
            MoodDetailDialog(
                selectedDate = selectedDate,
                onDismiss = { viewModel.dismissDialog() },
                moodList = moodByDateState
            )
        }
    }
}