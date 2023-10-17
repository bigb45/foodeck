package com.example.authentication.presentation.screens.auth.data

import com.example.authentication.util.ValidationResult

data class FieldError(
    val isError: Boolean = false,
    val errorMessage: ValidationResult? = null,
    )

