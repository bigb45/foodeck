package com.example.authentication.util

data class FieldError(
    val isError: Boolean = false,
    val errorMessage: ValidationResult? = null,
    )