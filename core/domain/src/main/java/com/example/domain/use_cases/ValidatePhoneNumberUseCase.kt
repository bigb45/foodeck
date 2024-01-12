package com.example.domain.use_cases

import com.example.data.util.TextFieldMessages
import com.example.data.util.ValidationUtil
import com.example.data.models.FieldError

import javax.inject.Inject

class ValidatePhoneNumberUseCase @Inject constructor(private val validationUtil: ValidationUtil) {
    operator fun invoke(phoneNumber: String): FieldError {

        if (phoneNumber.isEmpty()) {
            return FieldError(
                true, TextFieldMessages.EMPTY_PHONE_NUMBER
            )
        }
        if (!validationUtil.validatePhoneNumber(phoneNumber)) {
            return FieldError(
                true, TextFieldMessages.PHONE_NUMBER_INVALID
            )
        }
        return FieldError(false, null)
    }
}