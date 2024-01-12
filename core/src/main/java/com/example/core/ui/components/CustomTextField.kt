package com.example.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.FieldError
import com.example.compose.gray2
import com.example.core.ui.theme.inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: FieldError,
//    make ui state sealed interface (error state or success state)
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    val isError = error.isError
    val errorText = getStringResourceFromFieldError(fieldError = error)
    var isFocused by remember { mutableStateOf(false) }
    val externalBorderColor = if (isError) {
        colorScheme.error
    } else if (isFocused) {
        colorScheme.primary
    } else {
        gray2
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp, color = externalBorderColor, shape = RoundedCornerShape(16)
                )
                .padding(bottom = 12.dp, top = 8.dp, end = 8.dp, start = 8.dp)
                .fillMaxWidth()
//            .height(56.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                }

        ) {

            OutlinedTextField(

                label = {
                    Text(
                        label,
                        fontSize = if (isFocused || value.isNotEmpty()) 12.sp else 17.sp,
                        style = TextStyle(fontFamily = inter)
                    )
                },
                isError = isError,
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    focusedLabelColor = colorScheme.primary,
                    unfocusedLabelColor = gray2,
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)


            )

        }
        if (isError) {
            Text(errorText, style = TextStyle(fontSize = 12.sp, color = colorScheme.error))
        }
    }
}

