package com.example.domain.use_cases


import com.example.data.models.FieldError
import com.example.data.util.ValidationResult
import com.example.data.util.ValidationUtil
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor(private val validateUtil: ValidationUtil) {
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