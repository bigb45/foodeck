package com.example.domain.use_cases

import android.util.Log

import com.example.domain.data.FieldError
import com.example.domain.util.ValidationResult
import com.example.domain.util.ValidationUtil
import javax.inject.Inject


class ValidateEmailUseCase @Inject constructor(private val validationUtil: ValidationUtil) {
    operator fun invoke(email: String): FieldError {

        if (email.isEmpty()) {
            return FieldError(
                true, ValidationResult.EMPTY_EMAIL
            )
        }
        if (!validationUtil.validateEmail(email)) {
            Log.d("error:", "email invalid")
            return FieldError(
                true, ValidationResult.WRONG_PATTERN
            )
        }
        return FieldError(false, null)
    }
}