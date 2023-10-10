package com.example.authentication.domain.use_cases

import javax.inject.Inject

class ValidatePhoneNumberUseCase @Inject constructor(private val validationUtil: com.example.authentication.util.ValidationUtil) {
    operator fun invoke(phoneNumber: String): com.example.authentication.util.FieldError {

        if (phoneNumber.isEmpty()) {
            return com.example.authentication.util.FieldError(
                true,
                com.example.authentication.util.ValidationResult.EMPTY_PHONE_NUMBER
            )
        }
        if (!validationUtil.validatePhoneNumber(phoneNumber)) {
            return com.example.authentication.util.FieldError(
                true,
                com.example.authentication.util.ValidationResult.PHONE_NUMBER_INVALID
            )
        }
        return com.example.authentication.util.FieldError(false, null)
    }
}