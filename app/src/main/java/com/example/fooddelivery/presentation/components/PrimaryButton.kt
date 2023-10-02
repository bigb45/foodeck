package com.example.fooddelivery.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.fooddelivery.presentation.ui.theme.FoodDeliveryTheme


@Composable
fun PrimaryButton(
    text: String,
    state: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null
) {
    FoodDeliveryTheme {
        Button(
            onClick = onClick,
            enabled = state,
//            shape = RoundedCornerShape(20),
            modifier = Modifier
                .padding(8.dp)
                .width(328.dp)
                .height(62.dp)
                .background(color = Color(0xFFDD4B39), shape = RoundedCornerShape(size = 16.dp))
                .padding(start = 32.dp, top = 20.dp, end = 32.dp, bottom = 20.dp)

        ) {
            if (leadingIcon != null) {
                Icon(imageVector = leadingIcon, contentDescription = null)
            }
            Text(text)
            if (trailingIcon != null) {
                Icon(imageVector = trailingIcon, contentDescription = null)
            }


        }
    }

}