package com.example.authentication.presentation.screens.main_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.authentication.presentation.nav.MainNavigation
import com.example.core.ui.theme.FoodDeliveryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           FoodDeliveryTheme {
                Scaffold {
                    Surface(
                        modifier = Modifier
                            .padding(it)
                    ) {
                        MainNavigation()

                    }
                }
            }
        }
    }

}



