package com.example.authentication.domain.use_cases

import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor() {
    operator fun invoke(username: String): com.example.authentication.util.FieldError {

        if (username.isEmpty()) {
            return com.example.authentication.util.FieldError(
                true,
                com.example.authentication.util.ValidationResult.EMPTY_USERNAME
            )
        }
        return com.example.authentication.util.FieldError(false, null)
    }
}