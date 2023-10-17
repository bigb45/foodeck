package com.example.domain.use_cases

import com.example.authentication.presentation.screens.auth.data.FieldError
import com.example.authentication.util.ValidationResult
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor(private val validateUtil: com.example.authentication.util.ValidationUtil) {
    operator fun invoke(password: String): FieldError {
        if (password.isBlank()) {
            return FieldError(
                true,
                ValidationResult.EMPTY_PASSWORD
            )
        }
        if (password.length < 8) {
            return FieldError(
                true,
                ValidationResult.PASSWORD_LENGTH
            )
        }
        if (!validateUtil.validatePassword(password)) {
            return FieldError(
                true,
                ValidationResult.PASSWORD_INVALID
            )
        }
        return FieldError(false, null)
    }
}