package com.example.domain.data

import com.example.domain.util.ValidationResult

data class FieldError(
    val isError: Boolean = false,
    val errorMessage: com.example.domain.util.ValidationResult? = null,
    )

