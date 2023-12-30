package com.example.data.models

import com.example.data.util.TextFieldMessages

data class FieldError(
    val isError: Boolean = false,
    val errorMessage: TextFieldMessages? = null,
    )

