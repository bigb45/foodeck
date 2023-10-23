package com.example.domain.use_cases

import com.example.data.data.FieldError
import com.example.data.util.ValidationResult
import com.example.data.util.ValidationUtil
import javax.inject.Inject


class ValidateEmailUseCase @Inject constructor(private val validationUtil: ValidationUtil) {
    operator fun invoke(email: String): FieldError {

        if (email.isEmpty()) {
            return FieldError(
                true, ValidationResult.EMPTY_EMAIL
            )
        }
        if (!validationUtil.validateEmail(email)) {
            return FieldError(
                true, ValidationResult.WRONG_PATTERN
            )
        }
        return FieldError(false, null)
    }
}