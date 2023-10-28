package com.example.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.seed
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.core.ui.theme.interBold


@Composable
fun PrimaryButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    colors: ButtonColors? = null,
) {
    FoodDeliveryTheme {
        Button(
            onClick = onClick,
            enabled = enabled,
            shape = RoundedCornerShape(16.dp),
            colors = colors?: ButtonDefaults.buttonColors(),
            modifier = modifier
//                .padding(start = 32.dp, top = 20.dp, end = 32.dp, bottom = 20.dp)
                .height(62.dp)
//                .border(width = 1.dp, color = gray1, shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()

        ) {
            if (leadingIcon != null) {
                Icon(imageVector = leadingIcon, contentDescription = null)
            }
            Text(
                text,
                style = TextStyle(
                    fontSize = 17.sp,
                    fontFamily = interBold

                )
            )
            if (trailingIcon != null) {
                Icon(imageVector = trailingIcon, contentDescription = null)
            }


        }
    }

}

