package com.example.domain.use_cases

import com.example.domain.data.FieldError
import com.example.domain.util.ValidationResult
import com.example.domain.util.ValidationUtil
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