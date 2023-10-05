package com.example.fooddelivery.domain.use_cases

import com.example.fooddelivery.util.FieldError
import com.example.fooddelivery.util.ValidationResult
import com.example.fooddelivery.util.ValidationUtil
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor(private val validateUtil: ValidationUtil) {
    operator fun invoke(password: String): FieldError {
        if (password.isBlank()) {
            return FieldError(true, ValidationResult.EMPTY_PASSWORD)
        }
        if(password.length !in (8..16)){
            return FieldError(true, ValidationResult.PASSWORD_LENGTH)
        }
        if(!validateUtil.validatePassword(password)){
            return FieldError(true, ValidationResult.PASSWORD_INVALID)
        }
        return FieldError(false,null)
    }
}