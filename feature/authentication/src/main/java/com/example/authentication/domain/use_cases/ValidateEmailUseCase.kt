package com.example.authentication.domain.use_cases

import android.util.Log
import com.example.authentication.presentation.screens.auth.data.FieldError
import com.example.authentication.util.ValidationResult
import javax.inject.Inject


class ValidateEmailUseCase @Inject constructor(private val validationUtil: com.example.authentication.util.ValidationUtil) {
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