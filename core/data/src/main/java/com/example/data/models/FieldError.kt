package com.example.data.models

import com.example.data.util.ValidationResult

data class FieldError(
    val isError: Boolean = false,
    val errorMessage: com.example.data.util.ValidationResult? = null,
    )

