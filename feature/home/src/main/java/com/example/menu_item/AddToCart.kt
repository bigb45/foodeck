package com.example.menu_item

import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.theme.Typography
import com.example.core.ui.theme.interBold
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CartBottomBar(
    modifier: Modifier = Modifier,
    onAddToCartClick: () -> Unit,
    totalPrice: Float,
) {
    d("error", "${totalPrice}")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "$${String.format("%.2f", totalPrice)}",
            style = TextStyle(fontFamily = interBold, fontSize = 32.sp),
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = onAddToCartClick,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                "Add to cart",
                style = Typography.titleLarge.copy(fontFamily = interBold, color = Color.White),
                modifier = Modifier
            )
        }
    }
}