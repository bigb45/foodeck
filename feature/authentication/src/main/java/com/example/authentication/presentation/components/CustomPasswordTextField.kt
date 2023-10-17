package com.example.authentication.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authentication.presentation.screens.auth.data.FieldError
import com.example.authentication.util.ValidationResult
import com.example.compose.gray2
import com.example.core.ui.theme.inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: FieldError,
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
    var passwordVisible by remember { mutableStateOf(false) }

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
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                isError = isError,
                value = value,
                trailingIcon = {
                    val icon =
                        if (passwordVisible && value.isNotEmpty()) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                },
                onValueChange = onValueChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedLabelColor = colorScheme.primary,
                    unfocusedLabelColor = gray2,
                    errorBorderColor = Color.Transparent


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

@Composable
fun getStringResourceFromFieldError(fieldError: FieldError): String {
    val id = fieldError.errorMessage?.message ?: return ""
    return stringResource(id = id)
}
@Composable
fun getStringResourceFromFieldError(fieldError: ValidationResult?): String {
    val id = fieldError?.message ?: return ""
    return stringResource(id = id)
}
