package com.example.domain.use_cases

import com.example.authentication.presentation.screens.auth.data.FieldError
import com.example.authentication.util.ValidationResult
import javax.inject.Inject

class ValidatePhoneNumberUseCase @Inject constructor(private val validationUtil: com.example.authentication.util.ValidationUtil) {
    operator fun invoke(phoneNumber: String): FieldError {

        if (phoneNumber.isEmpty()) {
            return FieldError(
                true,
                ValidationResult.EMPTY_PHONE_NUMBER
            )
        }
        if (!validationUtil.validatePhoneNumber(phoneNumber)) {
            return FieldError(
                true,
                ValidationResult.PHONE_NUMBER_INVALID
            )
        }
        return FieldError(false, null)
    }
}