package com.example.menu_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MenuItemScreen() {
    val viewModel: MenuItemViewModel = hiltViewModel()
    Box(
        modifier = Modifier
            .background(Red)
    ){
        Text("this is the menu item of id ${viewModel.menuItemId}")
    }
}