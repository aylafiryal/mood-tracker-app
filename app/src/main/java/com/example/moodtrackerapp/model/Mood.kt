package com.example.moodtrackerapp.model

import android.R.attr.entries
import android.icu.lang.UCharacter.JoiningGroup.SAD
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.moodtrackerapp.R

enum class Mood(
    val apiKey: String,      // String yang dikirim ke Backend Golang / simpan DB
    val label: String,       // Nama tampilan UI
    @DrawableRes val iconRes: Int? = null // Asset PNG dari Clip Studio Paint
) {
    HAPPY(
        apiKey = "HAPPY",
        label = "Senang",
//        iconRes = R.drawable.ic_mood_happy
    ),
    SAD(
        apiKey = "SAD",
        label = "Sedih",
//        iconRes = R.drawable.ic_mood_sad
    ),
    ANXIOUS(
        apiKey = "ANXIOUS",
        label = "Cemas",
//        iconRes = R.drawable.ic_mood_anxious
    ),
    DEPRESSED(
        apiKey = "DEPRESSED",
        label = "Muram",
//        iconRes = R.drawable.ic_mood_depressed
    ),
    NEUTRAL(
        apiKey = "NEUTRAL",
        label = "Netral",
//        iconRes = R.drawable.ic_mood_neutral
    ),
    STRESSED(
        apiKey = "STRESSED",
        label = "Stres",
//        iconRes = R.drawable.ic_mood_stressed
    );

    companion object {
        // Fungsi helper untuk mapping string dari Response Backend Golang ke Enum Kotlin
        fun fromApiKey(key: String?): Mood {
            return entries.find { it.apiKey.equals(key, ignoreCase = true) } ?: NEUTRAL
        }
    }
}
