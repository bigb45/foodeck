package com.example.domain.use_cases

import com.example.data.util.ValidationResult
import com.example.data.models.FieldError
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