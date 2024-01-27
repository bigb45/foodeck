package com.example.menu_item


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.gray2
import com.example.core.ui.theme.Typography
import com.example.core.ui.theme.interBold
import com.example.data.repositories.Option

@Composable
fun CheckBoxSelector(
    data: CheckBoxSelectorData,
    selectedOptions: Map<String, List<Option>>,
    onSelectionChange: (String, Option, Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(colorScheme.surface)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(data.title, style = Typography.titleLarge.copy(fontFamily = interBold))
            if (data.required) {
                Text("required", color = colorScheme.primary)
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            data.options.forEach {
                val option = it.optionName
                val formattedPrice = String.format("%.2f", it.price)
                val isSelected = (selectedOptions[data.id] ?: emptyList()).contains(it)

                Row(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            onClick = {
                                onSelectionChange(data.id, it,  !isSelected)
                            },
                            interactionSource = interactionSource
                        )
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Checkbox(checked = isSelected, onCheckedChange = { isSelected ->
                        onSelectionChange(data.id, it, isSelected)
                    })

                    Text(
                        option, style = Typography.bodyLarge, modifier = Modifier
                            .weight(1f)
                    )

                    Text(
                        "+${data.currency}$formattedPrice",
                        style = Typography.bodyLarge.copy(color = if (isSelected) colorScheme.primary else gray2)
                    )
                }
            }

        }
    }

}

data class CheckBoxSelectorData(
    val id: String,
    val title: String,
    val options: List<Option>,
    val currency: String,
    val required: Boolean,
)