package com.example.menu_item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose.gray2
import com.example.compose.gray6
import com.example.core.ui.theme.Typography
import com.example.core.ui.theme.interBold

@Composable
fun Counter(
    modifier: Modifier = Modifier,
    counter: Int,
    increment: () -> Unit,
    decrement: () -> Unit,

    ) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Quantity", style = Typography.titleLarge.copy(fontFamily = interBold))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = gray6)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { decrement() }, modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Outlined.Remove, contentDescription = null, tint = gray2)
            }
            Text(
                counter.toString(),
                style = Typography.titleLarge,
                modifier = Modifier
                    .weight(5f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = { increment() }, modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null, tint = gray2)
            }
        }
    }
}
