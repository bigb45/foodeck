package com.example.fooddelivery.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddelivery.presentation.ui.theme.FoodDeliveryTheme


@Composable
fun CustomOutlinedButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
) {
    FoodDeliveryTheme {
        OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            shape = RoundedCornerShape(16),
            modifier = modifier.height(62.dp)

        ) {
            if (leadingIcon != null) {
                Icon(imageVector = leadingIcon, contentDescription = null)
            }
            Text(
                text, style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700),
                    textAlign = TextAlign.Center,
                )
            )
            if (trailingIcon != null) {
                Icon(imageVector = trailingIcon, contentDescription = null)
            }


        }
    }

}
