package com.example.authentication.domain.use_cases

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor(private val validateUtil: com.example.authentication.util.ValidationUtil) {
    operator fun invoke(password: String): com.example.authentication.util.FieldError {
        if (password.isBlank()) {
            return com.example.authentication.util.FieldError(
                true,
                com.example.authentication.util.ValidationResult.EMPTY_PASSWORD
            )
        }
        if (password.length < 8) {
            return com.example.authentication.util.FieldError(
                true,
                com.example.authentication.util.ValidationResult.PASSWORD_LENGTH
            )
        }
        if (!validateUtil.validatePassword(password)) {
            return com.example.authentication.util.FieldError(
                true,
                com.example.authentication.util.ValidationResult.PASSWORD_INVALID
            )
        }
        return com.example.authentication.util.FieldError(false, null)
    }
}