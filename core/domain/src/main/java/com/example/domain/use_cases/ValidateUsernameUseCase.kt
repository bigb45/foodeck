package com.example.domain.use_cases

import com.example.data.util.TextFieldMessages
import com.example.data.models.FieldError
import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor() {
    operator fun invoke(username: String): FieldError {

        if (username.isEmpty()) {
            return FieldError(
                true,
                TextFieldMessages.EMPTY_USERNAME
            )
        }
        return FieldError(false, null)
    }
}