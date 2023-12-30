package com.example.domain.use_cases


import com.example.data.models.FieldError
import com.example.data.util.TextFieldMessages
import com.example.data.util.ValidationUtil
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor(private val validateUtil: ValidationUtil) {
    operator fun invoke(password: String): FieldError {
        if (password.isBlank()) {
            return FieldError(
                true,
                TextFieldMessages.EMPTY_PASSWORD
            )
        }
        if (password.length < 8) {
            return FieldError(
                true,
                TextFieldMessages.PASSWORD_LENGTH
            )
        }
        if (!validateUtil.validatePassword(password)) {
            return FieldError(
                true,
                TextFieldMessages.PASSWORD_INVALID
            )
        }
        return FieldError(false, null)
    }
}