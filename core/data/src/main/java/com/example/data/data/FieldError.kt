package com.example.data.data

import com.example.data.util.ValidationResult

data class FieldError(
    val isError: Boolean = false,
    val errorMessage: ValidationResult? = null,
    )

