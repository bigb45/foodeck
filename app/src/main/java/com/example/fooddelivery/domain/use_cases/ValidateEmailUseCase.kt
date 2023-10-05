package com.example.fooddelivery.domain.use_cases

import com.example.fooddelivery.util.FieldError
import com.example.fooddelivery.util.ValidationResult
import com.example.fooddelivery.util.ValidationUtil
import javax.inject.Inject


class ValidateEmailUseCase @Inject constructor(private val validationUtil: ValidationUtil){
    operator fun invoke(email: String): FieldError {

        if(email.isEmpty()){
            return FieldError(true, ValidationResult.EMPTY_EMAIL)
        }
        if(!validationUtil.validateEmail(email)){
        return FieldError(true, ValidationResult.WRONG_PATTERN)
        }
            return FieldError(false, null)
    }
}