package com.example.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.gray1
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.core.ui.theme.interBold


@Composable
fun SecondaryButton(
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
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
//                .padding(start = 32.dp, top = 20.dp, end = 32.dp, bottom = 20.dp)
                .height(62.dp)
                .border(width = 1.dp, color = gray1, shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()

        ) {
            if (leadingIcon != null) {
                Icon(imageVector = leadingIcon, contentDescription = null)
            }
            Text(
                text,
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 22.sp,
                    fontFamily = interBold,
//                    fontWeight = FontWeight(900),
                    color = Color(0xFF8E8E93),
                    textAlign = TextAlign.Center,
                )
            )
            if (trailingIcon != null) {
                Icon(imageVector = trailingIcon, contentDescription = null)
            }


        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SecondaryButtonPreview() {
      Column{
          SecondaryButton(
              text = "Create an account instead",
              true,
              {},


              )
          SecondaryButton(
              text = "Login instead",
              true,
              {},


              )
      }
}

