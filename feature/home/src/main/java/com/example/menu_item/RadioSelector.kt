package com.example.menu_item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.compose.gray2
import com.example.core.ui.theme.Typography
import com.example.core.ui.theme.interBold
import com.example.data.models.Option
import com.example.data.models.SectionData

@Composable
fun RadioSelector(
    data: SectionData,
    selectedOption: Option?,
    sectionSelected: Boolean,
    onSelectionChange: (String, Option) -> Unit,
    ) {
    val interactionSource = remember { MutableInteractionSource() }
    val titleStyle = remember { Typography.titleLarge.copy(fontFamily = interBold) }
    val bodyLargeStyle = remember { Typography.bodyLarge }
    val gray2Color = remember { gray2 }

    Column(
        modifier = Modifier
            .background(colorScheme.surface)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if(!sectionSelected){
            Text("Please make a selection")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(data.title, style = titleStyle.copy(fontFamily = interBold))
            if (data.required) {
                Text("required", color = colorScheme.primary)
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            data.options.forEach {
                val id = it.id
                val option = it.optionName
                val formattedPrice = String.format("%.2f", it.price)
                val selected = selectedOption?.id == id
                Row(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            onClick = { onSelectionChange(data.id, it) },
                            interactionSource = interactionSource
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    RadioButton(selected = selected, onClick = {
                        onSelectionChange(data.id, it)
                    })

                    Text(option, style = bodyLargeStyle, modifier = Modifier
                        .weight(1f))

                    Text("+${data.currency}$formattedPrice", style = bodyLargeStyle.copy(color = if(selected) colorScheme.primary else gray2Color))
                }
            }

        }
    }

}
