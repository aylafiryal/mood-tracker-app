package com.example.moodtrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.moodtrackerapp.moodcalendar.MoodCalendarScreen
import com.example.moodtrackerapp.ui.theme.MoodTrackerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                MoodCalendarScreen()
            }
        }
    }
}