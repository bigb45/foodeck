package com.example.domain.use_cases

import com.example.authentication.presentation.screens.auth.data.FieldError
import com.example.authentication.util.ValidationResult
import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor() {
    operator fun invoke(username: String): FieldError {

        if (username.isEmpty()) {
            return FieldError(
                true,
                ValidationResult.EMPTY_USERNAME
            )
        }
        return FieldError(false, null)
    }
}