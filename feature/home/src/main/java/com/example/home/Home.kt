package com.example.home

import android.util.Log.d
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.core.ui.components.SecondaryButton

@Composable
fun Home() {
    Column {
        SecondaryButton(text = "Home", enabled = true, onClick = { d("Congrats", "You made it to the homepage") })
    }
}