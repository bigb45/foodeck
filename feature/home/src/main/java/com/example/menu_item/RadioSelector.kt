package com.example.menu_item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.compose.gray2
import com.example.core.ui.theme.Typography
import com.example.core.ui.theme.interBold

@Composable
fun RadioSelector(
    data: RadioSelectorData,
    selectedOption: String?,
    onSelectionChange: (String) -> Unit,

    ) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(data.title, style = Typography.titleLarge.copy(fontFamily = interBold))
            if (data.required) {
                Text("required", color = colorScheme.primary)
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
        ) {
            data.options.forEach {
                val option = it.key
                val formattedPrice = String.format("%.2f", it.value)
                val selected = selectedOption == option
                Row(
                    modifier = Modifier
                        .clickable(

                            indication = null,
                            onClick = { onSelectionChange(option)
                            },
                            interactionSource = interactionSource
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    RadioButton(selected = selected, onClick = {
                        onSelectionChange(
                            option
                        )
                    })

                    Text(option, style = Typography.bodyLarge, modifier = Modifier
                        .weight(1f))

                    Text("+${data.currency}$formattedPrice", style = Typography.bodyLarge.copy(color = if(selected) colorScheme.primary else gray2))
                }
            }

        }
    }

}

data class RadioSelectorData(
    val title: String,
    val options: Map<String, Float>,
    val currency: String,
    val required: Boolean,
)