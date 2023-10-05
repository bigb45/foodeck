package com.example.fooddelivery.domain.use_cases

import com.example.fooddelivery.util.FieldError
import com.example.fooddelivery.util.ValidationResult
import com.example.fooddelivery.util.ValidationUtil
import javax.inject.Inject

class ValidatePhoneNumberUseCase @Inject constructor(private val validationUtil: ValidationUtil) {
    operator fun invoke(phoneNumber: String): FieldError {

        if(phoneNumber.isEmpty()){
            return FieldError(true, ValidationResult.EMPTY_PHONE_NUMBER)
        }
        if(!validationUtil.validatePhoneNumber(phoneNumber)){
        return FieldError(true, ValidationResult.PHONE_NUMBER_INVALID)
        }
            return FieldError(false, null)
    }
}