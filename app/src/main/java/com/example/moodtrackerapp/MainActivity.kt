package com.example.moodtrackerapp

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moodtrackerapp.ui.theme.MoodTrackerAppTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                        month.getDatesOfMonth().map { it to false }.toImmutableList()
                    }
                    /*
                        NOTES:
                        Fungsi "remember" berguna untuk mempertahankan nilai sebuah variable.
                        Kalau mau pakai keyword "by", hasil di dalam blok remember mesti yang bisa didelegasikan.
                        Tipe data hasil yang bisa didelegasikan syaratnya adalah: punya fungsi getValue.
                        Biar kalo mau ubah value, bisa langsung dari variabelnya sendiri.
                     */

                    CalendarView(
                        month = month,
                        date = dates,
                        displayNext = true,
                        displayPrev = true,
                        onClickNext = { month = month.addMonths(1) },
                        onClickPrev = { month = month.addMonths(-1) },
                        onClick = { clickedDate -> /* nanti: buka form mood */ },
                        startFromSunday = true,
                        modifier = Modifier.padding(innerPadding)
                    )
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

private fun Date.formatToCalendarDay(): String = SimpleDateFormat("d", Locale.getDefault()).format(this)

@Composable
private fun CalendarCell(
    date: Date,
    signal: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = date.formatToCalendarDay()
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(10.dp)
            .background(
                shape = RoundedCornerShape(CornerSize(8.dp)),
                color = Color.Magenta
            )
            .clip(RoundedCornerShape(CornerSize(8.dp)))
            .clickable(onClick = onClick)
    ) {
        if (signal) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(
                        shape = CircleShape,
                        color = Color.Blue
                    )
            )
        }
        Text(
            text = text,
            color = colorScheme.onSecondaryContainer,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

private fun Int.getDayOfWeek3Letters(): String? = Calendar.getInstance().apply {
    set(Calendar.DAY_OF_WEEK, this@getDayOfWeek3Letters)
}.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())

@Composable
private fun WeekdayCell(weekday: Int, modifier: Modifier = Modifier) {
    val text = weekday.getDayOfWeek3Letters()
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .background( color = Color.Blue )
    ) {
        Text(
            text = text.orEmpty(),
            color = colorScheme.onPrimaryContainer,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

fun Date.formatToWeekDay(): Int =
    Calendar.getInstance().apply { time = this@formatToWeekDay }.get(Calendar.DAY_OF_WEEK)

@Composable
private fun CalendarGrid(
    date: ImmutableList<Pair<Date, Boolean>>,
    onClick: (Date) -> Unit,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier,
) {
    val weekdayFirstDay = date.first().first.formatToWeekDay()
    val weekdays = getWeekDays(startFromSunday)
    CalendarCustomLayout(modifier = modifier) {
        weekdays.forEach {
            WeekdayCell(weekday = it)
        }
        // Adds Spacers to align the first day of the month to the correct weekday
        val firstDayOfWeek = if (startFromSunday) 1 else 2
        repeat((weekdayFirstDay - firstDayOfWeek + 7) % 7) {
            Spacer(modifier = Modifier)
        }
        date.forEach {
            CalendarCell(date = it.first, signal = it.second, onClick = { onClick(it.first) })
        }
    }
}

fun getWeekDays(startFromSunday: Boolean): ImmutableList<Int> {
    val lista = (1..7).toList()
    return (if (startFromSunday) lista else lista.drop(1) + lista.take(1)).toImmutableList()
}

@Composable
private fun CalendarCustomLayout(
    modifier: Modifier = Modifier,
    horizontalGapDp: Dp = 2.dp,
    verticalGapDp: Dp = 2.dp,
    content: @Composable () -> Unit,
) {
    /*
        NOTES (rumus fungsi with): 'with this object, do the following.'
        'with' is used to access an object's members and methods without having to refer to the object once per access.
        It is (mostly) for abbreviating your code.

        LINK: https://stackoverflow.com/questions/45873205/what-does-with-mean-in-kotlin
     */
    val horizontalGap = with(LocalDensity.current) {
        horizontalGapDp.roundToPx()
    }
    val verticalGap = with(LocalDensity.current) {
        verticalGapDp.roundToPx()
    }
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val totalWidthWithoutGap = constraints.maxWidth - (horizontalGap * 6)
        val singleWidth = totalWidthWithoutGap / 7

        /*
            NOTES: var is variable (mutable); val is value (immutable);
         */
        val xPos: MutableList<Int> = mutableListOf()
        val yPos: MutableList<Int> = mutableListOf()
        var currentX = 0
        var currentY = 0
        measurables.forEach { _ ->
            xPos.add(currentX)
            yPos.add(currentY)
            if (currentX + singleWidth + horizontalGap > totalWidthWithoutGap) {
                currentX = 0
                currentY += singleWidth + verticalGap
            } else {
                currentX += singleWidth + horizontalGap
            }
        }

        val placeables: List<Placeable> = measurables.map { measurable ->
            measurable.measure(constraints.copy(maxHeight = singleWidth, maxWidth = singleWidth))
        }

        layout(
            width = constraints.maxWidth,
            height = currentY + singleWidth + verticalGap,
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
                .background(color = Color.Magenta)
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
        Spacer(modifier = Modifier.size(16.dp))
        if (!date.isNullOrEmpty()) {
            CalendarGrid(
                date = date,
                onClick = onClick,
                startFromSunday = startFromSunday,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .background( color = Color.LightGray )
            )
        }
    }
}

fun Date.formatToMonthString(): String = SimpleDateFormat("MMMM", Locale.getDefault()).format(this)

@Composable
private fun CustomIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = colorScheme.onPrimaryContainer,
        )
    }
}

private fun Date.getDatesOfMonth(): ImmutableList<Date> {
    val cal = Calendar.getInstance().apply { time = this@getDatesOfMonth }
    val totalDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    return (1..totalDays).map { day ->
        cal.set(Calendar.DAY_OF_MONTH, day)
        cal.time
    }.toImmutableList()
}

private fun Date.addMonths(amount: Int): Date =
    Calendar.getInstance().apply {
        time = this@addMonths
        add(Calendar.MONTH, amount)
    }.time