package com.example.menu_item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.gray2
import com.example.compose.gray6
import com.example.core.ui.theme.Typography
import com.example.core.ui.theme.interBold


@Composable
fun Instructions(
    modifier: Modifier = Modifier,
    onTextChange: () -> Unit,
    text: String,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Instructions", style = Typography.titleLarge.copy(fontFamily = interBold))
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Let us know if you have specific things in mind",
                style = Typography.bodyLarge.copy(color = gray2)
            )
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = gray6)
                .height(54.dp),
                value = text,
                onValueChange = { onTextChange() },
                shape = RoundedCornerShape(16.dp),
                placeholder = {
                    Text(
                        "e.g. less spices, no mayo etc",
                        style = Typography.titleMedium.copy(color = gray2)
                    )
                }

            )
        }
    }
}
